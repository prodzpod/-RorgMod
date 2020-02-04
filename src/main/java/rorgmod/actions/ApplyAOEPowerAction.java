//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rorgmod.RorgMod;

public class ApplyAOEPowerAction extends AbstractGameAction {
    private AbstractOrb orb;
    private AbstractPower power;

    public ApplyAOEPowerAction(AbstractPower power) {
        this.source = AbstractDungeon.player;
        this.power = power;
        this.actionType = ActionType.DEBUFF;
    }

    public void update() {
        int stack = power.amount;

        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (monster.isDeadOrEscaped() || monster.halfDead) continue;
            AbstractPower res = null;
            try {
                res = (AbstractPower) power.getClass().
                        getDeclaredConstructor(AbstractCreature.class, int.class).
                        newInstance(monster, stack);
            } catch (Exception e) {
                try {
                    res = (AbstractPower) power.getClass().
                            getDeclaredConstructor(AbstractCreature.class, int.class, boolean.class).
                            newInstance(monster, stack, false);
                } catch (Exception ex) {
                    RorgMod.logger.error("AOE Power error");
                    RorgMod.logger.error(ex);
                }
            }
            if (res != null) this.addToBot(new ApplyPowerAction(monster, source, res, stack, true, AttackEffect.NONE));
        }

        this.isDone = true;
    }
}
