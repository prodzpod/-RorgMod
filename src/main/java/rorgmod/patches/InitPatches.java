package rorgmod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.monsters.city.SnakePlant;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.cards.ironclad.ImmolateUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CatalystUnlock;
import com.megacrit.cardcrawl.unlock.cards.silent.CorpseExplosionUnlock;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import rorgmod.RorgMod;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.Random;

public class InitPatches {
    /** CARDS **/

    @SpirePatch(
            clz= AbstractDungeon.class,
            method= "initializeCardPools"
    )
    public static class RemoveCards {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void initializeCardPools(AbstractDungeon __instance) {
            AbstractCard card;
            for (String rawCard : RorgMod.cardsToRemove) {
                RorgMod.logger.info("removing card");
                RorgMod.logger.info(rawCard);
                card = CardLibrary.getCard(rawCard);
                if (card.color == AbstractCard.CardColor.COLORLESS)
                    AbstractDungeon.colorlessCardPool.removeCard(rawCard);
                switch (card.rarity) {
                    case COMMON:
                        AbstractDungeon.commonCardPool.removeCard(rawCard);
                        break;
                    case UNCOMMON:
                        AbstractDungeon.uncommonCardPool.removeCard(rawCard);
                        break;
                    case RARE:
                        AbstractDungeon.rareCardPool.removeCard(rawCard);
                        break;
                    case CURSE:
                        AbstractDungeon.curseCardPool.removeCard(rawCard);
                        break;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.NewExprMatcher(CardGroup.class);
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
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

    /** RELICS **/

    @SpirePatch(
            clz= AbstractDungeon.class,
            method= "initializeRelicList"
    )
    public static class RemoveRelics {
        @SpirePrefixPatch
        public static void initializeRelicList(AbstractDungeon __instance) {
            for (String relic : RorgMod.relicsToRemove) {
                if (AbstractDungeon.relicsToRemoveOnStart.indexOf(relic) == -1)
                    RorgMod.logger.info("Removing Relic");
                RorgMod.logger.info(relic);
                AbstractDungeon.relicsToRemoveOnStart.add(relic);
            };
        }
    }

    /** POTIONS **/

    @SpirePatch(
            clz= PotionHelper.class,
            method= "getPotions"
    )
    public static class RemovePotions {
        @SpirePostfixPatch
        public static ArrayList<String> RemovePotions(ArrayList<String> __result, AbstractPlayer.PlayerClass c, boolean getAll) {
            for (String potion : RorgMod.potionToRemove)
                if (__result.contains(potion)) {
                    RorgMod.logger.info("removing potion");
                    RorgMod.logger.info(potion);
                    __result.remove(potion);
                }
            return __result;
        }
    }

    /** EVENTS **/

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
            for (String event : RorgMod.eventsToRemove)
                if (tmp.contains(event)) {
                    RorgMod.logger.info("removing event");
                    RorgMod.logger.info(event);
                    tmp.remove(event);
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
            clz = AbstractDungeon.class,
            method = "getShrine"
    )
    public static class ShrineSpawn {

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(ArrayList<String> tmp) {
            for (String event : RorgMod.eventsToRemove)
                if (tmp.contains(event)) {
                    RorgMod.logger.info("removing shared event");
                    RorgMod.logger.info(event);
                    tmp.remove(event);
                }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "get");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    /** MONSTERS **/

    @SpirePatch(
            clz= TheCity.class,
            method= "generateStrongEnemies"
    )
    public static class RemoveSnakePlant { // TODO: rework in 1.3
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"monsters"}
        )
        public static void Insert(ArrayList<MonsterInfo> monsters) {
            MonsterInfo monsterToRemove = null;
            for (MonsterInfo monster : monsters) if (monster.name == MonsterHelper.SNAKE_PLANT_ENC) monsterToRemove = monster;
            monsters.remove(monsterToRemove);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MonsterInfo.class, "normalizeWeights");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz= TheBeyond.class,
            method= "generateElites"
    )
    public static class RemoveNemesis { // TODO: rework + add more in 1.3
        @SpireInsertPatch(
                locator = RemoveSnakePlant.Locator.class,
                localvars = {"monsters"}
        )
        public static void Insert(ArrayList<MonsterInfo> monsters) {
            MonsterInfo monsterToRemove = null;
            for (MonsterInfo monster : monsters) if (monster.name == MonsterHelper.NEMESIS_ENC) monsterToRemove = monster;
            monsters.remove(monsterToRemove);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MonsterInfo.class, "normalizeWeights");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    /** CHANGES **/

    @SpirePatch(
            clz= AbstractCard.class,
            method= SpirePatch.CONSTRUCTOR,
            paramtypez= { String.class, String.class, String.class, int.class, String.class, AbstractCard.CardType.class, AbstractCard.CardColor.class, AbstractCard.CardRarity.class, AbstractCard.CardTarget.class, DamageInfo.DamageType.class }
    )
    public static class CardChanges {
        public static void Prefix(AbstractCard __instance, String id, String name, String imgUrl, @ByRef int[] cost, String rawDescription, AbstractCard.CardType type, @ByRef AbstractCard.CardColor[] color, @ByRef AbstractCard.CardRarity[] rarity, AbstractCard.CardTarget target, DamageInfo.DamageType dType) {
            for (RorgMod.CardChanges change : RorgMod.cardsToChange) {
                if (id.equals(change.ID)) {
                    if (change.RARITY != null) rarity[0] = change.RARITY;
                    if (change.COLOR != null) color[0] = change.COLOR;
                    if (change.COST != -1) cost[0] = change.COST;
                }
            }
        }
    }

    @SpirePatch(
            clz= AbstractRelic.class,
            method= SpirePatch.CONSTRUCTOR,
            paramtypez= { String.class, String.class, AbstractRelic.RelicTier.class, AbstractRelic.LandingSound.class }
    )
    public static class ChangeRelic {
        public static void Prefix(AbstractRelic __instance, String setId, String imgName, @ByRef AbstractRelic.RelicTier[] tier, AbstractRelic.LandingSound sfx) {
            for (RorgMod.RelicChanges change : RorgMod.relicsToChange) {
                if (setId.equals(change.ID)) tier[0] = change.tier;
            }
        }
    }

    @SpirePatch(
            clz= BackToBasics.class,
            method= "upgradeStrikeAndDefends"
    )
    public static class ChangeAncientWriting {
        public static void Prefix() {
            RorgMod.logger.info("patching Ancient Writings... (1/2)");
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE), MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT));
        }
    }

    @SpirePatch(
            clz= BackToBasics.class,
            method= SpirePatch.STATICINITIALIZER
    )
    public static class ChangeAncientWriting2 {
        private static boolean found = false;

        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess i) throws CannotCompileException {
                    if (!found && i.getFieldName().equals("OPTIONS")) {
                        found = true;
                        RorgMod.logger.info("patching Ancient Writings... (2/2)");
                        i.replace("{ $_ = " + CardCrawlGame.class.getName() + ".languagePack.getEventString(\"rorgmod:Ancient Writing\").OPTIONS; }");
                    }
                }
            };
        }
    }
}
