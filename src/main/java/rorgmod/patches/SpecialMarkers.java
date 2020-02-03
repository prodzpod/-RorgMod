package rorgmod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import com.megacrit.cardcrawl.screens.compendium.RelicViewScreen;
import javassist.CtBehavior;
import rorgmod.RorgMod;
import rorgmod.helpers.ListHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class SpecialMarkers {
    private static final float SPRITE_SCALE = 150.0f;

    private static Texture beta    = getTexture("rorgmod/other/beta.png");
    private static Texture vanilla = getTexture("rorgmod/other/vanilla.png");
    private static Texture tweaked = getTexture("rorgmod/other/tweaked.png");

    private static final String arrayList = ArrayList.class.getName();

    private static Texture getTexture(String path) {
        Texture retVal = new Texture(Gdx.files.internal(path), true);
        retVal.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        return retVal;
    }

    private static Texture getTextureFromCard(AbstractCard card) {
        if (!RorgMod.markers) return null;
        if (ListHelper.cardsInBeta  .contains(card.cardID)) return beta;
        if (ListHelper.cardsReworked.contains(card.cardID)) return vanilla;
        if (ListHelper.cardsTweaked .contains(card.cardID)) return tweaked;
        return null;
    }

    private static Texture getTextureFromRelic(AbstractRelic relic) {
        if (!RorgMod.markers) return null;
        if (ListHelper.relicsReworked.contains(relic.relicId)) return vanilla;
        if (ListHelper.relicsTweaked .contains(relic.relicId)) return tweaked;
        return null;
    }

    private static void render(Texture res, SpriteBatch sb, float x, float y, float scale, float rot, float scale2) {

        if (res != null) sb.draw(res,
                x, y, 75f, 115f,
                SPRITE_SCALE, SPRITE_SCALE, scale * scale2, scale * scale2, rot,
                0, 0, (int)SPRITE_SCALE, (int)SPRITE_SCALE, false, false
                );
    }

    @SpirePatch(clz=AbstractCard.class, method="renderInLibrary", paramtypez= {SpriteBatch.class})
    @SpirePatch(clz=AbstractCard.class, method="renderCard",      paramtypez= {SpriteBatch.class, boolean.class, boolean.class})
    public static class RenderCards {
        @SpireInsertPatch(
                locator= Locator.class
        )
        public static void Insert(AbstractCard card, SpriteBatch sb) {
            render(getTextureFromCard(card), sb, card.current_x, card.current_y, card.drawScale, card.angle, 0.5f);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderEnergy");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(clz=AbstractRelic.class, method="render", paramtypez= {SpriteBatch.class, boolean.class, Color.class})
    public static class RenderRelics {
        public static void Postfix(AbstractRelic relic, SpriteBatch sb) {
            render(getTextureFromRelic(relic), sb, relic.currentX, relic.currentY, relic.scale, 0, 0.33f);
        }
    }

    @SpirePatch(clz= CardLibraryScreen.class, method= "updateCards")
    public static class NonMarkerCompendium {
        @SpireInsertPatch(
                locator= Locator.class,
                localvars= {"cards"}
        )
        public static SpireReturn<?> Insert(CardLibraryScreen __instance, ArrayList<AbstractCard> cards) {
            if (RorgMod.markers) return SpireReturn.Continue();
            List<AbstractCard> cardsToRemove = ListHelper.cardsToRemove.stream().map(element -> CardLibrary.cards.get(element)
            ).filter(Objects::nonNull).collect(Collectors.toList());

            cards.removeAll(cardsToRemove);
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardLibraryScreen.class, "visibleCards");
                int[] res = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                res[0]++; // courtesy of bd
                return res;
            }
        }
    }

    @SpirePatch(clz= RelicViewScreen.class, method= "renderList")
    public static class NonMarkerRelicView {
        public static SpireReturn<?> Prefix(RelicViewScreen __instance, SpriteBatch sb, String msg, String desc, ArrayList<AbstractRelic> list) {
            if (RorgMod.markers) return SpireReturn.Continue();
            ArrayList<AbstractRelic> relicsToRemove = new ArrayList<>();
            for (AbstractRelic relic : list) if (ListHelper.relicsToRemove.contains(relic.relicId)) relicsToRemove.add(relic);
            list.removeAll(relicsToRemove);
            return SpireReturn.Continue();
        }
    }
}
