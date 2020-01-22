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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

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
            Constructor construct = null;
            AbstractPower res = null;
            try{
                construct = power.getClass().getDeclaredConstructor(AbstractCreature.class, int.class);
                res = (AbstractPower) construct.newInstance(monster, stack);
//                RorgMod.logger.info("RES WITH 2 PARAMS: OWNER - TYPE - AMOUNT - ATLAS");
            } catch (NoSuchMethodException e) {
                try {
                    construct = power.getClass().getDeclaredConstructor(AbstractCreature.class, int.class, boolean.class);
                    res = (AbstractPower) construct.newInstance(monster, stack, false);
//                    RorgMod.logger.info("RES WITH 3 PARAMS: OWNER - TYPE - AMOUNT - ATLAS");
                } catch (NoSuchMethodException ex) {
                    RorgMod.logger.error("AOE Power error");
                    RorgMod.logger.error(ex);
                } catch (IllegalAccessException ex) {
                    RorgMod.logger.error("AOE Power error");
                    RorgMod.logger.error(e);
                } catch (InstantiationException ex) {
                    RorgMod.logger.error("AOE Power error");
                    RorgMod.logger.error(e);
                } catch (InvocationTargetException ex) {
                    RorgMod.logger.error("AOE Power error");
                    RorgMod.logger.error(e);
                }
            } catch (IllegalAccessException e) {
                RorgMod.logger.error("AOE Power error");
                RorgMod.logger.error(e);
            } catch (InstantiationException e) {
                RorgMod.logger.error("AOE Power error");
                RorgMod.logger.error(e);
            } catch (InvocationTargetException e) {
                RorgMod.logger.error("AOE Power error");
                RorgMod.logger.error(e);
            }

//            RorgMod.logger.info(res.owner);
//            RorgMod.logger.info(res);
//            RorgMod.logger.info(res.amount);
//            RorgMod.logger.info(res.region128);

            this.addToBot(new ApplyPowerAction(monster, source, res, stack, true, AttackEffect.NONE));
        }

        this.isDone = true;
    }
}
