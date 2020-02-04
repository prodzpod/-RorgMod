package rorgmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import javassist.CtBehavior;
import rorgmod.helpers.MetricHelper;
import rorgmod.powers.AbstractRorgPower;
import rorgmod.powers.LockdownPower;
import rorgmod.relics.AbstractRorgRelic;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CustomTriggerPatches {
    @SpirePatch(
            clz= ApplyPowerAction.class,
            method= "update"
    )
    public static class onApplyPower {
        @SpireInsertPatch(
                locator= Locator.class,
                localvars= { "powerToApply", "target", "source" }
        )
        public static void Insert(ApplyPowerAction __instance, AbstractPower powerToApply, AbstractCreature target, AbstractCreature source) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof AbstractRorgRelic) {
                    ((AbstractRorgRelic) r).onApplyPower(powerToApply, target, source);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "iterator");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz= AbstractRoom.class,
            method= "update"
    )
    public static class onRewardScreen {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void Insert() {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof AbstractRorgRelic) {
                    ((AbstractRorgRelic) r).onRewardScreen();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CombatRewardScreen.class, "open");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz= GameActionManager.class,
            method= "incrementDiscard"
    )
    public static class onDiscardCard {
        public static void Postfix(boolean endOfTurn) {
            if (!AbstractDungeon.actionManager.turnHasEnded && !endOfTurn) {
                for (AbstractPower power : AbstractDungeon.player.powers) {
                    if (power instanceof AbstractRorgPower) ((AbstractRorgPower) power).onDiscardCard();
                }
            }
        }
    }

    @SpirePatch(clz= GameActionManager.class, method= "endTurn")
    public static class resetExhaustCount {
        public static void Postfix() {
            MetricHelper.totalExhaustedThisTurn = 0;
        }
    }

    @SpirePatch(clz= Lightning.class, method= "onEndOfTurn")
    @SpirePatch(clz= Frost.class, method= "onEndOfTurn")
    @SpirePatch(clz= Dark.class, method= "onEndOfTurn")
    @SpirePatch(clz= Plasma.class, method= "onStartOfTurn")
    public static class LockdownPatch {
        public static SpireReturn Prefix(AbstractOrb __instance) {
            for (AbstractPower power : AbstractDungeon.player.powers) if (power instanceof AbstractRorgPower)
                ((AbstractRorgPower) power).onOrbPassive(__instance);
            if (AbstractDungeon.player.hasPower(LockdownPower.POWER_ID)) return SpireReturn.Return(null);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz= AbstractCard.class, method= SpirePatch.CLASS)
    public static class AccuracyFix {
        public static SpireField<Boolean> accuracyHitting = new SpireField<>(() -> false);
    }

    @SpirePatch(clz= AbstractCard.class, method= "makeStatEquivalentCopy")
    public static class AccuracyFix2 {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            AccuracyFix.accuracyHitting.set(__result, AccuracyFix.accuracyHitting.get(__instance));
            return __result;
        }
    }
}
