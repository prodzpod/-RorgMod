package rorgmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.unique.BurnIncreaseAction;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.purple.DevaForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import javassist.expr.NewExpr;
import rorgmod.RorgMod;
import rorgmod.cards.Hexaburn;
import rorgmod.cards.WraithFormAltRework;
import rorgmod.cards.WraithFormRework;
import rorgmod.events.GremlinWheelRework;
import rorgmod.powers.OverheatPower;

@SuppressWarnings("unused")
public class OtherPatches {
    @SpirePatch(
            clz= Lightning.class,
            method= "onEvoke"
    )
    public static class OverheatPatch {
        @SpirePrefixPatch
        public static void onEvoke(Lightning __instance) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo.hasPower(OverheatPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(mo, mo, new VulnerablePower(mo, 1, true),
                                    1, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }

    @SpirePatch(
            clz= Hexaghost.class,
            method= "takeTurn"
    )
    public static class HexaghostPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr i) throws CannotCompileException {
                    if (i.getClassName().equals(MakeTempCardInDiscardAction.class.getName())) {
                        RorgMod.logger.info("Finding and fixing hexaghost attack (1/2)");
                        RorgMod.logger.info(i.getClassName());
                        i.replace("{ $_ = $proceed(this.burnUpgraded ? new rorgmod.cards.Hexaburn() : c, $2); }");
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz= BurnIncreaseAction.class,
            method= "update"
    )
    public static class HexaghostPatch2 {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr i) throws CannotCompileException {
                    if (i.getClassName().equals(ShowCardAndAddToDiscardEffect.class.getName())) {
                        RorgMod.logger.info("Finding and fixing hexaghost attack (2/2)");
                        RorgMod.logger.info(i.getClassName());
                        i.replace("{ $_ = $proceed(new " + Hexaburn.class.getName() + "()); }");
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz= AbstractOrb.class,
            method= "applyFocus"
    )
    public static class DefragPatch {

        @SpirePostfixPatch
        public static void applyFocus(AbstractOrb __instance) {
            RorgMod.logger.info("Patching reworked defrag");
            AbstractPower power = AbstractDungeon.player.getPower("rorgmod:Defragment");
            if (power != null && __instance instanceof Lightning) {
                int lightningAmount = -1;
                for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                    if (orb instanceof Lightning) lightningAmount++;
                }
                __instance.passiveAmount += power.amount * lightningAmount;
                __instance.evokeAmount   += power.amount * lightningAmount;
            }
        }
    }

    @SpirePatch(clz = ProceedButton.class, method = "update")
    public static class FixEvents { // from vexmod
        // Note: this should really be moved to BaseMod
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(Instanceof i) throws CannotCompileException {
                    try {
                        if (i.getType().getName().equals(Mushrooms.class.getName())) {
                            RorgMod.logger.info("Finding and gremlin wheel event proceed button");
                            i.replace("$_ = $proceed($$) || currentRoom.event instanceof " + GremlinWheelRework.class.getName() + ";");
                        }
                    } catch (NotFoundException e) {
                        RorgMod.logger.error("Combat proceed button patch broken.", e);
                    }
                }
            };
        }
    }

//    @SpirePatch(clz= Defect.class, method= "getStartingRelics")
//    public static class ShortCircuitInit {
//        public static SpireReturn<ArrayList<String>> Prefix() {
//            ArrayList<String> retVal = new ArrayList<String>() {{
//                add(ShortCircuit.ID);
//            }};
//            UnlockTracker.markRelicAsSeen(ShortCircuit.ID);
//            return SpireReturn.Return(retVal);
//        }
//    }

    @SpirePatch(clz= CustomModeScreen.class, method= "addNonDailyMods")
    public static class TrueFormChange {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr i) throws CannotCompileException {
                    if (i.getClassName().equals(String[].class.getName())) {
                        i.replace("$_ = new String[] {" + DemonForm.ID
                                + ", " + WraithFormRework.ID
                                + ", " + WraithFormAltRework.ID
                                + ", " + EchoForm.ID
                                + ", " + DevaForm.ID + "};");
                    }
                }
            };
        }
    }
}
