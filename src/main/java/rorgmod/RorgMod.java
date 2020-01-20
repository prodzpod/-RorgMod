package rorgmod;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rorgmod.cards.*;
import rorgmod.events.GremlinWheel;
import rorgmod.monsters.WheelGremlin;
import rorgmod.powers.NoPower;
import rorgmod.relics.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

import static basemod.BaseMod.registerModBadge;

@SpireInitializer
public class RorgMod implements EditCardsSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, EditStringsSubscriber, PostInitializeSubscriber, PostPowerApplySubscriber {

    public static Logger logger = LogManager.getLogger(RorgMod.class.getName());
    private static UIStrings uistrings;
    private static final String ID = "rorgmod:Rorgmod";
    private static SpireConfig config;

    public static boolean enableDebuffect;
    public static boolean enableBetaCards;

    public RorgMod() {
        BaseMod.subscribe(this);
        Properties defaults = new Properties();
        defaults.setProperty("enableDebuffect", "FALSE");
        defaults.setProperty("enableBetaCards", "FALSE");
        try {
            config = new SpireConfig("RRrroohrrRGHHhhh!!", "RorgModConfig", defaults);
        } catch (IOException e) {
            logger.error("RorgMod SpireConfig initialization failed:");
            e.printStackTrace();
        }
        logger.info("rorg! :>");
        switch (config.getString("enableDebuffect")) {
            case "TRUE":
                RorgMod.enableDebuffect = true;
                break;
            case "FALSE":
                RorgMod.enableDebuffect = false;
                break;
            default:
                RorgMod.enableDebuffect = false;
                logger.warn("Config was set wrong, setting default instead");
                break;
        }
        switch (config.getString("enableBetaCards")) {
            case "TRUE":
                RorgMod.enableBetaCards = true;
                break;
            case "FALSE":
                RorgMod.enableBetaCards = false;
                break;
            default:
                RorgMod.enableBetaCards = false;
                logger.warn("Config was set wrong, setting default instead");
                break;
        }
    }

    public static void initialize() {
        new RorgMod();
    }

    @Override
    public void receivePostInitialize() {
        logger.info("RRrroohrrRGHHhhh!!");
        uistrings = CardCrawlGame.languagePack.getUIString(ID);
        String[] TEXT = uistrings.TEXT;
        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton debuffect = new ModLabeledToggleButton(TEXT[2], 350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, enableDebuffect, settingsPanel, label -> {}, button -> {
            RorgMod.enableDebuffect = button.enabled;
            RorgMod.config.setString("enableDebuffect", enableDebuffect ? "TRUE" : "FALSE");
            try {RorgMod.config.save();} catch (IOException e) {
                logger.warn("Config save failed at:");
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(debuffect);

        ModLabeledToggleButton BetaCards = new ModLabeledToggleButton(TEXT[3], 350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, enableDebuffect, settingsPanel, label -> {}, button -> {
            RorgMod.enableBetaCards = button.enabled;
            RorgMod.config.setString("enableBetaCards", enableBetaCards ? "TRUE" : "FALSE");
            try {RorgMod.config.save();} catch (IOException e) {
                logger.warn("Config save failed at:");
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(BetaCards);

        ModLabel label = new ModLabel(TEXT[4], 350.0f, 600.0f, settingsPanel, me -> {});
        settingsPanel.addUIElement(label);

        Texture badgeTexture = new Texture("rorgmod/badge.png");
        registerModBadge(badgeTexture, TEXT[0], "prodzpod", TEXT[1], settingsPanel);

        BaseMod.addMonster(WheelGremlin.ID, WheelGremlin::new);

        BaseMod.addEvent(GremlinWheel.ID, GremlinWheel.class);
    }

    @Override
    public void receiveEditCards() {
        final AbstractCard cards[] = {
                new LooseScrews(),
                new ZingZap(),
                new Fusion(),
                new VentilationError(),
                new SQLInjection(),
                new Overcurrent(),
                new Refrigerate(),
                new Antivirus(),
                new LRUCache(),
                new CriticalSection(),
                new Fragmentation(),
                new PropelledFlight(),
                new PusherProps(),
                new Malware(),
                new RadarScan(),
                new SteadyAim(),
                new Centralize(),
                new Storm(),
                new Overheat(),
                new GoForTheEyes(),
                new HotSockets(),
                new MetalCoating(),
                new Biogeneration(),
                new Volatility(),
                new ThunderStrike(),
                new NeutronShower(),
                new Chaos(),
                new SearingBlow(),
                new Immolate(),
                new Hexaburn(),
                new Catalyst()
        };

        final AbstractCard cardsBeta[] = {
                new DenialOfService(),
                new BadSector(),
                new Shadowmeld(),
                new BlackIce()
        };

        for (AbstractCard card : cards) {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
        }

        if (this.enableBetaCards) {
            for (AbstractCard card : cardsBeta) {
                BaseMod.addCard(card);
                UnlockTracker.unlockCard(card.cardID);
            }
        }

        BaseMod.removeCard("Scrape", AbstractCard.CardColor.BLUE);
        BaseMod.removeCard("Aggregate", AbstractCard.CardColor.BLUE);
        BaseMod.removeCard("Fusion", AbstractCard.CardColor.BLUE);
        BaseMod.removeCard("Storm", AbstractCard.CardColor.BLUE);
        BaseMod.removeCard("Thunder Strike", AbstractCard.CardColor.BLUE);
        BaseMod.removeCard("Chaos", AbstractCard.CardColor.BLUE);
        BaseMod.removeCard("Searing Blow", AbstractCard.CardColor.RED);
        BaseMod.removeCard("Immolate", AbstractCard.CardColor.RED);
        BaseMod.removeCard("Warcry", AbstractCard.CardColor.RED);
        BaseMod.removeCard("Wraith Form v2", AbstractCard.CardColor.GREEN); // TODO: rework wraith forme
        BaseMod.removeCard("Corpse Explosion", AbstractCard.CardColor.GREEN); // TODO: rework ce
        BaseMod.removeCard("Underhanded Strike", AbstractCard.CardColor.GREEN); // TODO: rework sneaky strike
        BaseMod.removeCard("Catalyst", AbstractCard.CardColor.GREEN);
        BaseMod.removeCard("Apparition", AbstractCard.CardColor.COLORLESS);
    }

    @Override
    public void receiveEditRelics() {
        final AbstractRelic[] blueRelics = {
            new LooseGear(),
            new GlossyCoat(),
            new CoolantMedium(),
            new DataDiskRework(),
            new DecayingCore(),
            new CrookedMotherboard(),
            new LiquidMercury()
        };

        for (AbstractRelic relic : blueRelics) {
            BaseMod.addRelic(relic, RelicType.BLUE);
            UnlockTracker.addRelic(relic.relicId);
        }

        BaseMod.addRelic(new MummifiedHandRework(), RelicType.SHARED);
        BaseMod.addRelic(new EmptyCageRework(), RelicType.SHARED);
        BaseMod.addRelic(new CrimsonLotus(), RelicType.PURPLE);

        UnlockTracker.addRelic(MummifiedHandRework.ID);
        UnlockTracker.addRelic(EmptyCageRework.ID);
        UnlockTracker.addRelic(CrimsonLotus.ID);

        BaseMod.removeRelic(new DataDisk());
        BaseMod.removeRelic(new DeadBranch());
        BaseMod.removeRelic(new RunicCube());
        BaseMod.removeRelic(new EmptyCage());
        BaseMod.removeRelic(new TinyHouse()); // TODO: rework tiny house
        BaseMod.removeRelic(new MummifiedHand());
        BaseMod.removeRelic(new Melange());
        BaseMod.removeRelic(new DarkstonePeriapt());
        BaseMod.removeRelic(new VioletLotus());
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();

        String languageString = "rorgmod/strings/" + getLanguageString(Settings.language);
        String keywordStrings = Gdx.files.internal(languageString + "/keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Type typeToken = new TypeToken<Map<String, Keyword>>() {}.getType();

        Map<String, Keyword> keywords = (Map)gson.fromJson(keywordStrings, typeToken);

        keywords.forEach((k,v)->{
            // Keyword word = (Keyword)v;
            logger.info("Adding Keyword - " + v.NAMES[0]);
            BaseMod.addKeyword(v.NAMES, v.DESCRIPTION);
        });
    }

    @Override
    public void receiveEditStrings() {
        String languageString = "rorgmod/strings/" + getLanguageString(Settings.language);
        String cardStrings = Gdx.files.internal(languageString + "/cards.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String relicStrings = Gdx.files.internal(languageString + "/relics.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String monsterStrings = Gdx.files.internal(languageString + "/monsters.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(MonsterStrings.class, monsterStrings);
        String powerStrings = Gdx.files.internal(languageString + "/powers.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String eventStrings = Gdx.files.internal(languageString + "/events.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
        String uiStrings = Gdx.files.internal(languageString + "/ui.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
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
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        if (abstractPower instanceof IntangiblePlayerPower || abstractPower instanceof IntangiblePower) {
            int stack = abstractPower.amount;
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(target, source, abstractPower));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new NoPower(target, stack)));
        }

    }
}