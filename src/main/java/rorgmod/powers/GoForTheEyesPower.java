package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rorgmod.RorgMod;

import java.lang.reflect.InvocationTargetException;

public class GoForTheEyesPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Go for the Eyes";
    public AbstractPower latestPower = null;

    public GoForTheEyesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        RorgMod.logger.info("GFTE DEBUG: power.id, power.amount, power.owner, target");
//        RorgMod.logger.info(power);
//        RorgMod.logger.info(power.amount);
//        RorgMod.logger.info(power.owner);
//        RorgMod.logger.info(target);
        if (power != latestPower && source instanceof AbstractPlayer && target instanceof AbstractMonster && power.type == PowerType.DEBUFF) {
            this.flash();
            AbstractPower newPower = null;
            try {
                newPower = power.getClass().getConstructor(AbstractCreature.class, int.class).newInstance(target, amount);
            } catch (NoSuchMethodException e) {
                try {
                    newPower = power.getClass().getConstructor(AbstractCreature.class, int.class, boolean.class).newInstance(target, amount, false);
                } catch (InstantiationException ex) {
                    RorgMod.logger.error("Error Occured at GoForTheEyesPower");
                    RorgMod.logger.error(ex);
                } catch (IllegalAccessException ex) {
                    RorgMod.logger.error("Error Occured at GoForTheEyesPower");
                    RorgMod.logger.error(ex);
                } catch (InvocationTargetException ex) {
                    RorgMod.logger.error("Error Occured at GoForTheEyesPower");
                    RorgMod.logger.error(ex);
                } catch (NoSuchMethodException ex) {
                    RorgMod.logger.error("Error Occured at GoForTheEyesPower");
                    RorgMod.logger.error(ex);
                }
            } catch (IllegalAccessException e) {
                RorgMod.logger.error("Error Occured at GoForTheEyesPower");
                RorgMod.logger.error(e);
            } catch (InstantiationException e) {
                RorgMod.logger.error("Error Occured at GoForTheEyesPower");
                RorgMod.logger.error(e);
            } catch (InvocationTargetException e) {
                RorgMod.logger.error("Error Occured at GoForTheEyesPower");
                RorgMod.logger.error(e);
            }
            latestPower = newPower;
            this.addToTop(new ApplyPowerAction(target, owner, newPower));
        }
    }
}