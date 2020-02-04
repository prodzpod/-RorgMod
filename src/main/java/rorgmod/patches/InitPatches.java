package rorgmod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.exordium.GoldenIdolEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.Nemesis;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import rorgmod.RorgMod;
import rorgmod.cards.Ache;
import rorgmod.helpers.ListHelper;
import rorgmod.powers.ImmunityPower;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
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
        public static void Insert(AbstractDungeon __instance) {
            List<AbstractCard> cards = ListHelper.cardsToRemove.stream().map(element -> CardLibrary.cards.get(element)
            ).filter(Objects::nonNull).collect(Collectors.toList());
            RorgMod.logger.info(cards.size() + " Cards removed");
            AbstractDungeon.colorlessCardPool.group.removeAll(cards);
            AbstractDungeon.commonCardPool   .group.removeAll(cards);
            AbstractDungeon.uncommonCardPool .group.removeAll(cards);
            AbstractDungeon.rareCardPool     .group.removeAll(cards);
            AbstractDungeon.curseCardPool    .group.removeAll(cards);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.NewExprMatcher(CardGroup.class);
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(clz= CardLibrary.class, method="getAnyColorCard", paramtypez= {AbstractCard.CardType.class, AbstractCard.CardRarity.class})
    @SpirePatch(clz= CardLibrary.class, method="getAnyColorCard", paramtypez= {AbstractCard.CardRarity.class})
    public static class RemovePrismCards {
        @SpireInsertPatch(
                locator= Locator.class,
                localvars= {"anyCard"}
        )
        public static void Insert(CardGroup anyCard) {
            List<AbstractCard> cards = ListHelper.cardsToRemove.stream().map(element -> CardLibrary.cards.get(element)
            ).filter(Objects::nonNull).collect(Collectors.toList());
            anyCard.group.removeAll(cards);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "shuffle");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(clz= CardLibrary.class, method="getCurse", paramtypez= {})
    @SpirePatch(clz= CardLibrary.class, method="getCurse", paramtypez= {AbstractCard.class, Random.class})
    public static class RemoveRandomCurses {
        @SpireInsertPatch(
                locator= Locator.class,
                localvars= {"tmp"}
        )
        public static void Insert(ArrayList<String> tmp) {
            List<String> cards = ListHelper.cardsToRemove.stream().map(element -> CardLibrary.cards.get(element).cardID
            ).filter(Objects::nonNull).collect(Collectors.toList());
            tmp.removeAll(cards);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(HashMap.class, "get");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }

    /** RELICS **/

    @SpirePatch(
            clz= AbstractDungeon.class,
            method= "initializeRelicList"
    )
    public static class RemoveRelics {
        public static void Prefix(AbstractDungeon __instance) {
            RorgMod.logger.info(ListHelper.relicsToRemove.size() + " Relics removed");
            AbstractDungeon.relicsToRemoveOnStart.addAll(ListHelper.relicsToRemove);
        }
    }

    /** POTIONS **/

    @SpirePatch(
            clz= PotionHelper.class,
            method= "getPotions"
    )
    public static class RemovePotions {
        public static ArrayList<String> Postfix(ArrayList<String> __result, AbstractPlayer.PlayerClass c, boolean getAll) {
            RorgMod.logger.info(ListHelper.potionsToRemove.size() + " Potions removed");
            __result.removeAll(ListHelper.potionsToRemove);
            return __result;
        }
    }

    /** EVENTS **/

    @SpirePatch(clz= Exordium .class, method= "initializeEventList")
    @SpirePatch(clz= TheCity  .class, method= "initializeEventList")
    @SpirePatch(clz= TheBeyond.class, method= "initializeEventList")
    public static class EventSpawn {
        public static void Postfix() {
            RorgMod.logger.info(ListHelper.eventsToRemove.size() + " Events removed");
            Exordium .eventList.removeAll(ListHelper.eventsToRemove);
            TheCity  .eventList.removeAll(ListHelper.eventsToRemove);
            TheBeyond.eventList.removeAll(ListHelper.eventsToRemove);
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "initializeSpecialOneTimeEventList")
    @SpirePatch(clz= Exordium .class, method= "initializeShrineList")
    @SpirePatch(clz= TheCity  .class, method= "initializeShrineList")
    @SpirePatch(clz= TheBeyond.class, method= "initializeShrineList")
    public static class ShrineSpawn {
        public static void Postfix() {
                AbstractDungeon.specialOneTimeEventList.removeAll(ListHelper.eventsToRemove);
                Exordium .shrineList.removeAll(ListHelper.eventsToRemove);
                TheCity  .shrineList.removeAll(ListHelper.eventsToRemove);
                TheBeyond.shrineList.removeAll(ListHelper.eventsToRemove);
        }
    }

    /** MONSTERS **/

    @SpirePatch(
            clz= MonsterHelper.class,
            method= "getEncounter"
    )
    public static class PatchNewEncounters {
        public static SpireReturn<MonsterGroup> Prefix(String key) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
            RorgMod.logger.info(ListHelper.monstersToChange.size() + " Encounters edited");
            for (ListHelper.EncounterChanges change : ListHelper.monstersToChange)
                if (key.equals(change.ID)) {
                    AbstractMonster[] monsters = new AbstractMonster[change.monster.length];
                    for (int i = 0; i < change.monster.length; i++) {
                        ListHelper.Encounter enc = change.monster[i];
                        if (enc.xpos == enc.ypos && enc.xpos == -1f) monsters[i] = enc.monster.newInstance();
                        else monsters[i] = enc.monster.getConstructor(float.class, float.class).newInstance(enc.xpos, enc.ypos);
                    }
                    return SpireReturn.Return(new MonsterGroup(monsters));
                }
            return SpireReturn.Continue();
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
//            RorgMod.logger.info(ListHelper.cardsToTweak.size() + " Cards tweaked");
            for (ListHelper.CardChanges change : ListHelper.cardsToTweak) {
                if (id.equals(change.ID)) {
                    if (!ListHelper.cardsTweaked.contains(change.ID)) ListHelper.cardsTweaked.add(change.ID);
                    if (change.RARITY != null) rarity[0] = change.RARITY;
                    if (change.COLOR != null)  color [0] = change.COLOR;
                    if (change.COST != -1)     cost  [0] = change.COST;
                }
            }
        }
    }

    @SpirePatch(
            clz= AbstractRelic.class,
            method= SpirePatch.CONSTRUCTOR,
            paramtypez= { String.class, String.class, AbstractRelic.RelicTier.class, AbstractRelic.LandingSound.class }
    )
    public static class RelicChanges {
        public static void Prefix(AbstractRelic __instance, String setId, String imgName, @ByRef AbstractRelic.RelicTier[] tier, AbstractRelic.LandingSound sfx) {
//            RorgMod.logger.info(ListHelper.relicsToTweak.size() + " Relics tweaked");
            for (ListHelper.RelicChanges change : ListHelper.relicsToTweak) {
                if (setId.equals(change.ID)) {
                    if (!ListHelper.relicsTweaked.contains(change.ID)) ListHelper.relicsTweaked.add(change.ID);
                    tier[0] = change.tier;
                }
            }
        }
    }

    @SpirePatch(
            clz= BackToBasics.class,
            method= "upgradeStrikeAndDefends"
    )
    public static class ChangeAncientWriting {
        public static void Prefix() {
            RorgMod.logger.info("patching Ancient Writings...");
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE), MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT));
        }
    }

    @SpirePatch(clz= GoldenIdolEvent.class, method="buttonEffect")
    public static class TempPatchInjury {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall i) throws CannotCompileException {
                    if (i.getClassName().equals(CardLibrary.class.getName()) && i.getMethodName().equals("getCopy")) {
                        i.replace("{ $_ = $proceed(\"" + Ache.ID + "\"); }");
                    }
                }
            };
        }
    }

    @SpirePatch(clz= GoldenIdolEvent.class, method="buttonEffect")
    public static class TempPatchInjury2 {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(NewExpr i) throws CannotCompileException {
                    if (i.getClassName().equals(Injury.class.getName())) {
                        i.replace("{ $_ = new " + Ache.class.getName() + "(); }");
                    }
                }
            };
        }
    }

    @SpirePatch(clz= Nemesis.class, method= "takeTurn")
    public static class NemesisPatch {
        @SpireInsertPatch(locator= Locator.class)
        public static SpireReturn Insert(Nemesis __instance) {
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return(null);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Nemesis.class, "hasPower");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(clz= AbstractMonster.class, method= "usePreBattleAction")
    public static class NemesisPatch2 {
        public static void Prefix(AbstractMonster __instance) {
            if (__instance.id.equals(Nemesis.ID)) __instance.addToBot(new ApplyPowerAction(__instance, __instance, new ImmunityPower(__instance, 0), 0));
        }
    }
}
