package rorgmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import rorgmod.relics.AbstractRorgRelic;

import java.util.ArrayList;

public class RelicPatches {
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
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            }
        }
    }
}
