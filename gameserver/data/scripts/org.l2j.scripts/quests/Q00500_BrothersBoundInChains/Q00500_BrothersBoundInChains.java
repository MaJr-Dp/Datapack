package quests.Q00500_BrothersBoundInChains;

import org.l2j.gameserver.data.xml.impl.SkillData;
import org.l2j.gameserver.enums.QuestType;
import org.l2j.gameserver.model.actor.L2Attackable;
import org.l2j.gameserver.model.actor.L2Npc;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.model.events.Containers;
import org.l2j.gameserver.model.events.EventType;
import org.l2j.gameserver.model.events.ListenerRegisterType;
import org.l2j.gameserver.model.events.annotations.RegisterEvent;
import org.l2j.gameserver.model.events.annotations.RegisterType;
import org.l2j.gameserver.model.events.impl.character.npc.OnAttackableKill;
import org.l2j.gameserver.model.events.impl.character.player.OnPlayerSummonAgathion;
import org.l2j.gameserver.model.events.impl.character.player.OnPlayerUnsummonAgathion;
import org.l2j.gameserver.model.events.listeners.ConsumerEventListener;
import org.l2j.gameserver.model.quest.Quest;
import org.l2j.gameserver.model.quest.QuestState;
import org.l2j.gameserver.model.quest.State;
import org.l2j.gameserver.model.skills.Skill;

/**
 * Brothers Bound in Chains (500)
 * @author Mobius (Based on GoD quest.)
 * @URL https://l2wiki.com/classic/Brothers_Bound_in_Chains
 */
public class Q00500_BrothersBoundInChains extends Quest
{
	// NPC
	private static final int DARK_JUDGE = 30981;
	// Items
	private static final int GEMSTONE_B = 2132;
	private static final int PENITENT_MANACLES = 70806;
	private static final int CRUMBS_OF_PENITENCE = 70807;
	// Skill
	private static final int HOUR_OF_PENITENCE = 55702;
	// Agathion
	private static final int SIN_EATER = 9021;
	// Other
	private static final String KILL_COUNT_VAR = "killCount";
	
	public Q00500_BrothersBoundInChains()
	{
		super(500);
		addStartNpc(DARK_JUDGE);
		addTalkId(DARK_JUDGE);
		registerQuestItems(PENITENT_MANACLES, CRUMBS_OF_PENITENCE);
		
		Containers.Global().addListener(new ConsumerEventListener(Containers.Global(), EventType.ON_PLAYER_SUMMON_AGATHION, (OnPlayerSummonAgathion event) -> OnPlayerSummonAgathion(event), this));
		Containers.Global().addListener(new ConsumerEventListener(Containers.Global(), EventType.ON_PLAYER_UNSUMMON_AGATHION, (OnPlayerUnsummonAgathion event) -> OnPlayerUnsummonAgathion(event), this));
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return getNoQuestMsg(player);
		}
		
		switch (event)
		{
			case "buff":
			{
				if ((player != null) && (player.getAgathionId() == SIN_EATER))
				{
					final Skill skill = SkillData.getInstance().getSkill(HOUR_OF_PENITENCE, 1); // Hour of Penitence
					skill.activateSkill(player, player);
					startQuestTimer("buff", 270000, null, player); // Rebuff every 4min30 (retail like)
				}
				return null;
			}
			case "30981-02.htm":
			case "30981-03.htm":
			{
				break;
			}
			case "30981-04.htm":
			{
				if (getQuestItemsCount(player, GEMSTONE_B) >= 30)
				{
					takeItems(player, GEMSTONE_B, 30);
					giveItems(player, PENITENT_MANACLES, 1);
				}
				else
				{
					event = "30981-05.html";
				}
				break;
			}
			case "30981-06.htm":
			{
				qs.startQuest();
				break;
			}
			case "30981-09.html": // not retail html.
			{
				if (getQuestItemsCount(player, CRUMBS_OF_PENITENCE) >= 35)
				{
					takeItems(player, CRUMBS_OF_PENITENCE, -1);
					takeItems(player, PENITENT_MANACLES, -1);
					player.setPkKills(Math.max(0, player.getPkKills() - getRandom(1, 3)));
					qs.unset(KILL_COUNT_VAR);
					qs.exitQuest(QuestType.DAILY, true);
				}
				else
				{
					// If player delete QuestItems: Need check how it work on retail.
					qs.setCond(1);
					event = "30981-07.html";
				}
				break;
			}
			default:
			{
				event = getNoQuestMsg(player);
			}
		}
		
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		final QuestState qs = getQuestState(talker, true);
		String htmltext = getNoQuestMsg(talker);
		
		switch (qs.getState())
		{
			case State.CREATED:
			{
				htmltext = (talker.getPkKills() > 0) && (talker.getReputation() >= 0) ? "30981-01.htm" : "30981-nopk.htm";
				break;
			}
			case State.STARTED:
			{
				switch (qs.getCond())
				{
					case 1:
					{
						htmltext = "30981-07.html";
						break;
					}
					case 2:
					{
						htmltext = "30981-08.html"; // not retail html.
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				if (qs.isNowAvailable())
				{
					qs.setState(State.CREATED);
					htmltext = "30981-01.htm";
				}
				break;
			}
		}
		
		return htmltext;
	}
	
	private void OnPlayerSummonAgathion(OnPlayerSummonAgathion event)
	{
		if (event.getAgathionId() != SIN_EATER)
		{
			return;
		}
		final L2PcInstance player = event.getPlayer();
		if (player == null)
		{
			return;
		}
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return;
		}
		
		startQuestTimer("buff", 2500, null, player);
	}
	
	private void OnPlayerUnsummonAgathion(OnPlayerUnsummonAgathion event)
	{
		if (event.getAgathionId() != SIN_EATER)
		{
			return;
		}
		final L2PcInstance player = event.getPlayer();
		if (player == null)
		{
			return;
		}
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return;
		}
		
		cancelQuestTimer("buff", null, player);
		player.getEffectList().stopSkillEffects(true, HOUR_OF_PENITENCE);
	}
	
	@RegisterEvent(EventType.ON_ATTACKABLE_KILL)
	@RegisterType(ListenerRegisterType.GLOBAL_MONSTERS)
	public void onAttackableKill(OnAttackableKill event)
	{
		final L2PcInstance player = event.getAttacker();
		if ((player == null) || (player.getAgathionId() != SIN_EATER) || !player.getEffectList().isAffectedBySkill(HOUR_OF_PENITENCE))
		{
			return;
		}
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return;
		}
		final L2Attackable target = event.getTarget();
		if (target == null)
		{
			return;
		}
		
		// Retail prohibitions.
		if ((target.getLevel() - player.getLevel()) < -6)
		{
			return;
		}
		if (target.isRaid() || target.isRaidMinion())
		{
			return;
		}
		if (player.getCommandChannel() != null)
		{
			return;
		}
		
		// The quest item drops from every 20th mob you kill, in total you need to kill 700 mobs.
		final int killCount = qs.getInt(KILL_COUNT_VAR);
		if (killCount >= 20)
		{
			// Player can drop more than 35 Crumbs of Penitence but there's no point in getting more than 35 (retail).
			giveItems(player, CRUMBS_OF_PENITENCE, 1);
			qs.set(KILL_COUNT_VAR, 0);
			
			if (!qs.isCond(2) && (getQuestItemsCount(player, CRUMBS_OF_PENITENCE) >= 35))
			{
				qs.setCond(2, true);
			}
		}
		else
		{
			qs.set(KILL_COUNT_VAR, killCount + 1);
		}
	}
}
