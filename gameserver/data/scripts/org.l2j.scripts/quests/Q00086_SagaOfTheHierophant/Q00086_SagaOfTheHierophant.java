/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q00086_SagaOfTheHierophant;

import org.l2j.gameserver.Config;
import org.l2j.gameserver.model.actor.L2Npc;
import org.l2j.gameserver.model.actor.instance.L2PcInstance;
import org.l2j.gameserver.model.base.ClassId;
import org.l2j.gameserver.model.holders.NpcLogListHolder;
import org.l2j.gameserver.model.quest.Quest;
import org.l2j.gameserver.model.quest.QuestState;
import org.l2j.gameserver.model.quest.State;
import org.l2j.gameserver.network.serverpackets.MagicSkillUse;
import org.l2j.gameserver.util.Util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author QuangNguyen
 */
public class Q00086_SagaOfTheHierophant extends Quest
{
	// NPCs
	public final int ORVEN = 30857;
	public final int SERESIN = 30657;
	public final int RIFKEN = 34268;
	public final int LEONARD = 30861;
	public final int TABLET_OF_VISION_1 = 31646;
	public final int TABLET_OF_VISION_2 = 31648;
	public final int TABLET_OF_VISION_3 = 31650;
	public final int TABLET_OF_VISION_4 = 31654;
	public final int SUNSET_GUIDE_LUNA = 31591;
	// Monsters
	public final int ICE_MONSTER = 27316;
	public final int SPIRIT_OF_A_DROWNED = 27317;
	public final int SOUL_OF_COLD = 27318;
	public final int GHOST_OF_SOLITUDE = 27319;
	public final int FIEND_OF_COLD = 27320;
	public final int SPIRIT_OF_COLD = 27321;
	public final int SPAMPLAND_WATCHMAN = 21650;
	public final int FLAME_DRAKE = 21651;
	public final int FIERY_IFRIT = 21652;
	public final int IKEDIT = 21653;
	public final int GATEKEEPER_OF_THE_HOLY_EDICT = 27215;
	public final int FALLEN_ANGEL_NAVERIUS = 27269;
	public final int HALISHA_ARCHON = 27219;
	public final int FALLEN_ANGEL_TANAKIA = 27274;
	// Items
	public final int ICE_CRYSTAL_FRAGMENT = 49820;
	public final int HALISHA_BADGE = 7501;
	public final int RESONANCE_AMULET = 7284;
	public final int RESONANCE_AMULET_2 = 7315;
	public final int RESONANCE_AMULET_3 = 7346;
	public final int RESONANCE_AMULET_4 = 7377;
	public final int FOOD_SACK = 49836;
	// Reward
	public final int BOOK_GOLD_LION = 90038;
	// Misc
	private static final String KILL_COUNT_VAR = "KillCount";
	
	public Q00086_SagaOfTheHierophant()
	{
		super(86);
		addStartNpc(ORVEN);
		addTalkId(ORVEN, SERESIN, RIFKEN, LEONARD, SUNSET_GUIDE_LUNA, TABLET_OF_VISION_1, TABLET_OF_VISION_2, TABLET_OF_VISION_3, TABLET_OF_VISION_4);
		addKillId(ICE_MONSTER, SPIRIT_OF_A_DROWNED, SOUL_OF_COLD, GHOST_OF_SOLITUDE, FIEND_OF_COLD, SPIRIT_OF_COLD, SPAMPLAND_WATCHMAN, FLAME_DRAKE, FIERY_IFRIT, IKEDIT, GATEKEEPER_OF_THE_HOLY_EDICT, FALLEN_ANGEL_NAVERIUS, HALISHA_ARCHON, FALLEN_ANGEL_TANAKIA);
		registerQuestItems(FOOD_SACK, ICE_CRYSTAL_FRAGMENT, HALISHA_BADGE);
		addCondMinLevel(76, "aiken02.htm");
		addCondClassId(ClassId.PROPHET, "aiken03.htm");
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		String htmltext = event;
		switch (event)
		{
			case "aiken02a.htm":
			{
				htmltext = "aiken5.htm";
				break;
			}
			case "aiken01s.htm":
			{
				if (qs.isCond(0))
				{
					qs.startQuest();
					qs.setCond(1);
				}
				break;
			}
			case "jer2.htm":
			{
				if (qs.isCond(1))
				{
					qs.setCond(2);
				}
				break;
			}
			case "rifken2.htm":
			{
				if (qs.isCond(2))
				{
					qs.setCond(3);
				}
				break;
			}
			case "rifken4.htm":
			{
				if (qs.isCond(4))
				{
					qs.setCond(5);
					takeItems(player, ICE_CRYSTAL_FRAGMENT, -1);
					giveItems(player, FOOD_SACK, 1);
				}
				break;
			}
			case "jer4.htm":
			{
				if (qs.isCond(5))
				{
					qs.setCond(6);
					giveItems(player, RESONANCE_AMULET, 1);
					takeItems(player, FOOD_SACK, -1);
				}
				break;
			}
			case "stone12.htm":
			{
				if (qs.isCond(6))
				{
					qs.setCond(7);
				}
				break;
			}
			case "stone22.htm":
			{
				if (qs.isCond(8))
				{
					addSpawn(FALLEN_ANGEL_NAVERIUS, npc, true, 0, true);
					qs.setCond(9);
				}
				break;
			}
			case "stone25.htm":
			{
				if (qs.isCond(10))
				{
					qs.setCond(11);
				}
				break;
			}
			case "jer6.htm":
			{
				if (qs.isCond(11))
				{
					qs.setCond(12);
				}
				break;
			}
			case "stone32.htm":
			{
				if (qs.isCond(14))
				{
					qs.setCond(15);
				}
				break;
			}
			case "stone41.htm":
			{
				if (qs.isCond(15))
				{
					qs.setCond(16);
					addSpawn(FALLEN_ANGEL_TANAKIA, npc, true, 0, true);
				}
				break;
			}
			case "erikrams2.htm":
			{
				if (qs.isCond(16))
				{
					qs.setCond(17);
					giveItems(player, RESONANCE_AMULET_4, 1);
				}
				break;
			}
			case "stone43.htm":
			{
				if (qs.isCond(17))
				{
					qs.setCond(18);
				}
				break;
			}
			case "aiken7.htm":
			{
				if (qs.isCond(18))
				{
					if ((player.getLevel() < 76) || (player.getBaseClass() != 17))
					{
						htmltext = "30849-nolvl.htm";
					}
					else
					{
						addExpAndSp(player, 3100000, 103000);
						rewardItems(player, BOOK_GOLD_LION, 1);
						takeItems(player, RESONANCE_AMULET, -1);
						takeItems(player, RESONANCE_AMULET_2, -1);
						takeItems(player, RESONANCE_AMULET_3, -1);
						takeItems(player, RESONANCE_AMULET_4, -1);
						takeItems(player, HALISHA_BADGE, -1);
						qs.exitQuest(false, true);
						player.setClassId(98);
						player.setBaseClass(98);
						player.broadcastUserInfo();
						npc.broadcastPacket(new MagicSkillUse(npc, player, 5103, 1, 1000, 0));
					}
				}
				break;
			}
		}
		return htmltext;
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
				if (npc.getId() == ORVEN)
				{
					htmltext = "aiken01.htm";
				}
				break;
			}
			case State.STARTED:
			{
				switch (npc.getId())
				{
					case ORVEN:
					{
						if (qs.isCond(1))
						{
							htmltext = "aiken01s.htm";
						}
						else if (qs.isCond(18))
						{
							htmltext = "aiken6.htm";
						}
						break;
					}
					case SERESIN:
					{
						switch (qs.getCond())
						{
							case 1:
							{
								htmltext = "jer1.htm";
								break;
							}
							case 2:
							{
								htmltext = "jer2.htm";
								break;
							}
							case 5:
							{
								htmltext = "jer3.htm";
								break;
							}
							case 6:
							{
								htmltext = "jer4.htm";
								break;
							}
							case 11:
							{
								htmltext = "jer5.htm";
								break;
							}
							case 12:
							{
								htmltext = "jer6.htm";
								break;
							}
						}
						break;
					}
					case LEONARD:
					{
						if (qs.isCond(11))
						{
							htmltext = "jer5.htm";
						}
						else if (qs.isCond(12))
						{
							htmltext = "jer6.htm";
						}
						break;
					}
					case RIFKEN:
					{
						switch (qs.getCond())
						{
							case 2:
							{
								htmltext = "rifken1.htm";
								break;
							}
							case 3:
							{
								htmltext = "rifken2.htm";
								break;
							}
							case 4:
							{
								htmltext = "rifken3.htm";
								break;
							}
						}
						break;
					}
					case TABLET_OF_VISION_1:
					{
						if (qs.isCond(6))
						{
							htmltext = "stone11.htm";
						}
						else if (qs.isCond(7))
						{
							htmltext = "stone12.htm";
						}
						break;
					}
					case TABLET_OF_VISION_2:
					{
						switch (qs.getCond())
						{
							case 8:
							{
								htmltext = "stone21.htm";
								break;
							}
							case 9:
							{
								htmltext = "stone23.htm";
								break;
							}
							case 10:
							{
								htmltext = "stone24.htm";
								break;
							}
							case 11:
							{
								htmltext = "stone25.htm";
								break;
							}
						}
						break;
					}
					case TABLET_OF_VISION_3:
					{
						if (qs.isCond(14))
						{
							htmltext = "stone31.htm";
						}
						else if (qs.isCond(15))
						{
							htmltext = "stone32.htm";
						}
						break;
					}
					case TABLET_OF_VISION_4:
					{
						switch (qs.getCond())
						{
							case 15:
							{
								htmltext = "stone40.htm";
								break;
							}
							case 17:
							{
								htmltext = "stone42.htm";
								break;
							}
							case 18:
							{
								htmltext = "stone43.htm";
								break;
							}
						}
						break;
					}
					case SUNSET_GUIDE_LUNA:
					{
						if (qs.isCond(16))
						{
							htmltext = "erikrams1.htm";
						}
						else if (qs.isCond(17))
						{
							htmltext = "erikrams2.htm";
						}
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(talker);
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getQuestState(killer, false);
		if ((qs != null) && qs.isStarted() && Util.checkIfInRange(Config.ALT_PARTY_RANGE, npc, killer, true))
		{
			switch (npc.getId())
			{
				case ICE_MONSTER:
				case SPIRIT_OF_A_DROWNED:
				case SOUL_OF_COLD:
				case FIEND_OF_COLD:
				case GHOST_OF_SOLITUDE:
				case SPIRIT_OF_COLD:
				{
					if (qs.isCond(3))
					{
						if (giveItemRandomly(killer, npc, ICE_CRYSTAL_FRAGMENT, 1, 50, 0.5, true))
						{
							qs.setCond(4);
						}
					}
					break;
				}
				case FALLEN_ANGEL_NAVERIUS:
				{
					if (qs.isCond(9))
					{
						qs.setCond(10);
					}
					break;
				}
				case SPAMPLAND_WATCHMAN:
				case FLAME_DRAKE:
				case FIERY_IFRIT:
				case IKEDIT:
				{
					if (qs.isCond(12))
					{
						if (giveItemRandomly(killer, npc, HALISHA_BADGE, 1, 700, 0.5, true))
						{
							addSpawn(HALISHA_ARCHON, npc, true, 0, true);
							qs.setCond(13);
						}
					}
					break;
				}
				case HALISHA_ARCHON:
				{
					if (qs.isCond(13))
					{
						giveItems(killer, RESONANCE_AMULET_3, 1, true);
						qs.setCond(14);
					}
					break;
				}
				case FALLEN_ANGEL_TANAKIA:
				{
					if (qs.isCond(16))
					{
						addSpawn(SUNSET_GUIDE_LUNA, npc, true, 20000, true);
					}
					break;
				}
				case GATEKEEPER_OF_THE_HOLY_EDICT:
				{
					if (qs.isCond(7))
					{
						final int count = qs.getInt(KILL_COUNT_VAR);
						if (count < 20)
						{
							qs.set(KILL_COUNT_VAR, count + 1);
						}
						if (count >= 20)
						{
							qs.setCond(8, true);
							giveItems(killer, RESONANCE_AMULET_2, 1);
						}
					}
					break;
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public Set<NpcLogListHolder> getNpcLogList(L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if ((qs != null) && qs.isCond(7))
		{
			final Set<NpcLogListHolder> holder = new HashSet<>(1);
			
			// guardian of forbidden knowledge
			final int guardiancount = qs.getInt(KILL_COUNT_VAR);
			if (guardiancount > 0)
			{
				holder.add(new NpcLogListHolder(GATEKEEPER_OF_THE_HOLY_EDICT, false, guardiancount));
			}
			return holder;
		}
		return super.getNpcLogList(player);
	}
}