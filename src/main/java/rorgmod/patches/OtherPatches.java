package rorgmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.BurnIncreaseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.cards.ironclad.ImmolateUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CatalystUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CorpseExplosionUnlock;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import rorgmod.RorgMod;
import rorgmod.powers.OverheatPower;

import java.util.Iterator;

public class OtherPatches {
    @SpirePatch(
            clz= Lightning.class,
            method= "onEvoke"
    )
    public static class OverheatPatch {
        @SpirePrefixPatch
        public static void onEvoke(Lightning __instance) {
            Iterator<AbstractMonster> var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            AbstractMonster mo;
            while(var3.hasNext()) {
                mo = var3.next();
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
                    if (i.getClassName().equals("com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction")) {
                        RorgMod.logger.info("Finding and fixing hexaghost attack");
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
                    if (i.getClassName().equals("com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect")) {
                        RorgMod.logger.info("Finding and fixing hexaghost attack");
                        RorgMod.logger.info(i.getClassName());
                        i.replace("{ $_ = $proceed(new rorgmod.cards.Hexaburn()); }");
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz= ImmolateUnlock.class,
            method= SpirePatch.CONSTRUCTOR
    )
    public static class ImmolatePatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("getCard")) {
                        RorgMod.logger.info("Fixing ImmolateUnlock");
                        m.replace("{ $_ = com.megacrit.cardcrawl.helpers.CardLibrary.getCard(\"rorgmod:Immolate\"); }");
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz= CatalystUnlock.class,
            method= SpirePatch.CONSTRUCTOR
    )
    public static class CatalystPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("getCard")) {
                        RorgMod.logger.info("Fixing CatalystUnlock");
                        m.replace("{ $_ = com.megacrit.cardcrawl.helpers.CardLibrary.getCard(\"rorgmod:Catalyst\"); }");
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz= CorpseExplosionUnlock.class,
            method= SpirePatch.CONSTRUCTOR
    )
    public static class CorpseExplosionPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("getCard")) {
                        RorgMod.logger.info("Fixing CorpseExplosionUnlock");
                        m.replace("{ $_ = new rorgmod.cards.CorpseExplosion(); }");
                    }
                }
            };
        }
    }
}
