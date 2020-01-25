package rorgmod.patches;

import basemod.BaseMod;
import com.badlogic.gdx.Game;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.BurnIncreaseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.Mushrooms;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.potions.GhostInAJar;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.cards.ironclad.ImmolateUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CatalystUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CorpseExplosionUnlock;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import org.apache.logging.log4j.Logger;
import rorgmod.RorgMod;
import rorgmod.cards.Hexaburn;
import rorgmod.events.GremlinWheel;
import rorgmod.powers.AbstractRorgPower;
import rorgmod.powers.OverheatPower;
import rorgmod.relics.AbstractRorgRelic;

import java.util.ArrayList;
import java.util.Iterator;

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
                            i.replace("$_ = $proceed($$) || currentRoom.event instanceof " + GremlinWheel.class.getName() + ";");
                        }
                    } catch (NotFoundException e) {
                        RorgMod.logger.error("Combat proceed button patch broken.", e);
                    }
                }
            };
        }
    }

}
