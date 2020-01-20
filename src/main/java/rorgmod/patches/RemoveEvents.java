package rorgmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.city.ForgottenAltar;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.GhostInAJar;
import com.megacrit.cardcrawl.unlock.cards.ironclad.ImmolateUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CatalystUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CorpseExplosionUnlock;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import rorgmod.RorgMod;

import java.util.ArrayList;


public class RemoveEvents {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getEvent"
    )
    public static class EventSpawn {

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(ArrayList<String> tmp) {
            if (true) {
                tmp.remove(Ghosts.ID);
                tmp.remove(GremlinWheelGame.ID);
                tmp.remove(ForgottenAltar.ID);
                tmp.remove(BackToBasics.ID);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
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

    @SpirePatch(
            clz= PotionHelper.class,
            method= "getPotions"
    )
    public static class RemoveGhostInAJar {
        public static ArrayList<String> PostFix(ArrayList<String> __result, PotionHelper __instance, AbstractPlayer.PlayerClass c, boolean getAll) {
            __result.remove(GhostInAJar.POTION_ID);
            return __result;
        }
    }

    @SpirePatch(
            clz= AbstractDungeon.class,
            method= "initializeRelicList"
    )
    public static class RemoveRelics {
        @SpirePrefixPatch
        public static void initializeRelicList(AbstractDungeon __instance) {
            for (String relic : RorgMod.relicsToRemove) { // 2nd instance
                // RorgMod.logger.info("Removing Relic");
                RorgMod.logger.info(relic);
                AbstractDungeon.relicsToRemoveOnStart.add(relic);
            };
        }
    }
}
