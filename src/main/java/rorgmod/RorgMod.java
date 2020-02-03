package rorgmod;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.devcommands.ConsoleCommand;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rorgmod.cards.AbstractRorgCard;
import rorgmod.helpers.ListHelper;
import rorgmod.helpers.MetricHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import static basemod.BaseMod.registerModBadge;

@SpireInitializer
public class RorgMod implements EditCardsSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, EditStringsSubscriber, PostInitializeSubscriber, PostExhaustSubscriber, PostDrawSubscriber {
    public static Logger logger = LogManager.getLogger(RorgMod.class.getName());
    private static final String ID = "rorgmod:Rorgmod";
    private static SpireConfig config;

    public static boolean enableDebuffect;
    public static boolean enableBetaCards;
    public static boolean markers;
    public static boolean forceLoad;

    public RorgMod() {
        BaseMod.subscribe(this);
        Properties defaults = new Properties();
        defaults.setProperty("enableDebuffect", "false");
        defaults.setProperty("enableBetaCards", "false");
        defaults.setProperty("markers", "false");
        defaults.setProperty("forceLoad", "false");
        try {
            config = new SpireConfig("RRrroohrrRGHHhhh!!", "RorgModConfig", defaults);
        } catch (IOException e) {
            logger.error("RorgMod SpireConfig initialization failed:");
            e.printStackTrace();
        }
        logger.info("RRrrohrRGHHhhh!!");
        enableDebuffect = config.getBool("enableDebuffect");
        enableBetaCards = config.getBool("enableBetaCards");
        markers         = config.getBool("markers");
        forceLoad       = config.getBool("forceLoad");
    }

    public static void initialize() {
        new RorgMod();
    }

    @Override
    public void receivePostInitialize() {
        logger.info("RRrroohrrRGHHhhh!!");
        logger.info("CONFIG SETTING:");
        logger.info("Enable Beta");
        logger.info(enableBetaCards);
        logger.info("Use Markers");
        logger.info(markers);
        logger.info("Force Loading");
        logger.info(forceLoad);
        final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

        // BADGES // CONFIGS
        ModPanel settingsPanel = new ModPanel();

        addCheckBox("enableDebuffect", TEXT[2], 700f, settingsPanel);
        addCheckBox("enableBetaCards", TEXT[3], 650f, settingsPanel);
        addCheckBox("markers"        , TEXT[4], 600f, settingsPanel);
        addCheckBox("forceLoad"      , TEXT[5], 550f, settingsPanel);

        ModLabel label = new ModLabel(TEXT[6], 350.0f, 600.0f, settingsPanel, me -> {});
        settingsPanel.addUIElement(label);

        Texture badgeTexture = new Texture("rorgmod/badge.png");
        registerModBadge(badgeTexture, TEXT[0], "prodzpod", TEXT[1], settingsPanel);

        // OTHER ADDS
        for (ListHelper.EventAdds   event   : ListHelper.eventsToAdd  ) BaseMod.addEvent         (event.id   , event.event);
        for (ListHelper.MonsterAdds monster : ListHelper.monstersToAdd) BaseMod.addMonster       (monster.id , monster.getMonster);
        for (ListHelper.CommandAdds command : ListHelper.commandsToAdd) ConsoleCommand.addCommand(command.key, command.command);
    }

    private void addCheckBox(final String field, String desc, final float ypos, ModPanel panel) {
        panel.addUIElement(new ModLabeledToggleButton(desc, 350.0f, ypos, Settings.CREAM_COLOR, FontHelper.charDescFont, enableDebuffect, panel, label -> {}, button -> {
            try {
                RorgMod.class.getField(field).set(this, button.enabled);
                config.setBool(field, RorgMod.class.getField(field).getBoolean(this));
            } catch (NoSuchFieldException | IllegalAccessException e) { e.printStackTrace(); }
            try { config.save(); } catch (IOException e) { e.printStackTrace(); }
        }));
    }

    @Override
    public void receiveEditCards() {
        List<AbstractRorgCard> cards = ListHelper.cardsToAdd.stream().map(element -> {
            try { return element.newInstance(); } catch (Exception ex) { ex.printStackTrace(); }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        logger.info(cards.size() + " Cards added");
        for (AbstractRorgCard card : cards) if (!card.BETA || enableBetaCards) {
            if (card.BETA)              ListHelper.cardsInBeta.add(card.cardID);
            if (card.REWORK_ID != null) ListHelper.cardsReworked.add(card.REWORK_ID);
            BaseMod.addCard(card);
            UnlockTracker.addCard(card.ID);
        }
    }

    @Override
    public void receiveEditRelics() {
        List<ListHelper.PostRelicAdds> relics = ListHelper.relicsToAdd.stream().map(element -> {
            try { return new ListHelper.PostRelicAdds(element); } catch (Exception ex) { ex.printStackTrace(); }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        logger.info(relics.size() + " Relics added");
        for (ListHelper.PostRelicAdds relic : relics) if (!relic.relic.BETA || enableBetaCards) {
            if (relic.relic.BETA)              ListHelper.relicsInBeta.add(relic.relic.relicId);
            if (relic.relic.REWORK_ID != null) ListHelper.relicsReworked.add(relic.relic.REWORK_ID);
            BaseMod.addRelic(relic.relic, relic.type);
            UnlockTracker.addRelic(relic.relic.relicId);
        }
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();

        String keywordStrings = getEditStrings("keywords");

        Type typeToken = new TypeToken<Map<String, Keyword>>() {}.getType();
        Map<String, Keyword> keywords = gson.fromJson(keywordStrings, typeToken);

        logger.info(keywords.size() + " Keywords Added");
        keywords.forEach((k,v)-> BaseMod.addKeyword(v.NAMES, v.DESCRIPTION));
    }

    @Override
    public void receiveEditStrings() {
        loadStrings(CardStrings   .class, "cards");
        loadStrings(RelicStrings  .class, "relics");
        loadStrings(PotionStrings .class, "potions");
        loadStrings(EventStrings  .class, "events");
        loadStrings(MonsterStrings.class, "monsters");
        loadStrings(PowerStrings  .class, "powers");
        loadStrings(UIStrings     .class, "ui");
    }

    private void loadStrings(Class<?> strings, String jsonPath) {
        BaseMod.loadCustomStrings(strings, getEditStrings(jsonPath));
    }

    private String getEditStrings(String jsonPath) {
        return Gdx.files.internal("rorgmod/strings/" + getLanguageString(Settings.language) + "/" + jsonPath + ".json").readString(String.valueOf(StandardCharsets.UTF_8));
    }

    private String getLanguageString(Settings.GameLanguage language) {
        switch (language) {
            case KOR:
                return "kor";
            default:
                return "eng";
        }
    }

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        MetricHelper.totalExhaustedThisTurn++;
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        for (ListHelper.CardChanges change : ListHelper.cardsToTweak)
            if (abstractCard.cardID.equals(change.ID))
                if (change.REDRAW_FOR_CURSES) {
//                    RorgMod.logger.info("curse debug: redraw triggered");
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(1));
                }
    }

    // these used to be a "intangible to NoPower" subscriber. but now that I'm 400% sure all source of intangible is removed/reworked, there is no need.
    // modded source of intangible can do whatever /shrug
}