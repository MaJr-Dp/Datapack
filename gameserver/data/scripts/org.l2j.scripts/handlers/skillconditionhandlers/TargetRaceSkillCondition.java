package handlers.skillconditionhandlers;

import org.l2j.gameserver.enums.Race;
import org.l2j.gameserver.model.StatsSet;
import org.l2j.gameserver.model.WorldObject;
import org.l2j.gameserver.model.actor.Creature;
import org.l2j.gameserver.model.skills.ISkillCondition;
import org.l2j.gameserver.model.skills.Skill;

import static org.l2j.gameserver.util.GameUtils.isCreature;

/**
 * @author UnAfraid
 */
public class TargetRaceSkillCondition implements ISkillCondition {

	public final Race race;
	
	public TargetRaceSkillCondition(StatsSet params)
	{
		race = params.getEnum("race", Race.class);
	}
	
	@Override
	public boolean canUse(Creature caster, Skill skill, WorldObject target) {
		if (!isCreature(target)) {
			return false;
		}
		final Creature targetCreature = (Creature) target;
		return targetCreature.getRace() == race;
	}
}
