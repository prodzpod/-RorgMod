package rorgmod;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.devcommands.ConsoleCommand;
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
import com.megacrit.cardcrawl.cards.blue.Reboot;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.cards.green.Alchemize;
import com.megacrit.cardcrawl.cards.green.CorpseExplosion;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.cards.purple.FollowUp;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.colorless.SadisticNature;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SecretPortal;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.city.ForgottenAltar;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.GhostInAJar;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rorgmod.cards.*;
import rorgmod.commands.*;
import rorgmod.events.*;
import rorgmod.monsters.*;
import rorgmod.powers.NoPower;
import rorgmod.relics.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

import static basemod.BaseMod.registerModBadge;
import static basemod.BaseMod.removePotion;

@SpireInitializer
public class RorgMod implements EditCardsSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, EditStringsSubscriber, PostInitializeSubscriber, PostPowerApplySubscriber {
//TODO : ADD ALL REMOVE/CHANGES IN THE NEW FORMAT, AND DEBUG
    public static Logger logger = LogManager.getLogger(RorgMod.class.getName());
    private static UIStrings uistrings;
    private static final String ID = "rorgmod:Rorgmod";
    private static SpireConfig config;
    public static String cardsToRemove[] = {
            Scrape.ID,
            Aggregate.ID,
            Fusion.ID, // REWORKED
            GoForTheEyes.ID, // REWORKED
            Storm.ID, // REWORKED
            ThunderStrike.ID, // REWORKED
            Chaos.ID, // REWORKED
            SearingBlow.ID, // REWORKED
            Immolate.ID, // REWORKED
            Warcry.ID,
            WraithForm.ID,
            CorpseExplosion.ID,
            SneakyStrike.ID,
            Catalyst.ID, // REWORKED
            Apparition.ID,
            Defragment.ID, // REWORKED
            BiasedCognition.ID, // REWORKED
            Leap.ID, // REWORKED
            RipAndTear.ID,
            BeamCell.ID,
            SteamBarrier.ID,
            AutoShields.ID, // REWORKED
            Blasphemy.ID,
            StormOfSteel.ID, // REWORKED
            Claw.ID, // REWORKED
            Accuracy.ID, // REWORKED
            Reflex.ID, // REWORKED
            PhantasmalKiller.ID, // REWORKED
            FollowUp.ID, // REWORKED
            Prepared.ID, // REWORKED
            Slice.ID // REWORKED
    };

    public static String relicsToRemove[] = {
            DeadBranch.ID,
            EmptyCage.ID, // REWORKED
            TinyHouse.ID,
            Melange.ID,
            DarkstonePeriapt.ID,
            VioletLotus.ID,
            IncenseBurner.ID
    };

    public static String eventsToRemove[] = {
            Ghosts.ID,
            GremlinWheelGame.ID, // REWORKED
            ForgottenAltar.ID,
            BackToBasics.ID, // REWORKED
            SecretPortal.ID
    };

    public static String potionToRemove[] = {
            GhostInAJar.POTION_ID
    };

    public static CardChanges cardsToChange[] = {
            new CardChanges(Reboot.ID, -1, null, AbstractCard.CardRarity.UNCOMMON),
            new CardChanges(Alchemize.ID, -1, AbstractCard.CardColor.COLORLESS, null),
            new CardChanges(SadisticNature.ID, -1, AbstractCard.CardColor.GREEN, null),
            new CardChanges(Envenom.ID, 1, null, null)
    };

    public static RelicChanges relicsToChange[] = {
            new RelicChanges(MummifiedHand.ID, AbstractRelic.RelicTier.RARE),
            new RelicChanges(WristBlade.ID, AbstractRelic.RelicTier.RARE),
            new RelicChanges(NinjaScroll.ID, AbstractRelic.RelicTier.SHOP)
    };

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
        BaseMod.addEvent(AncientWritings.ID, AncientWritings.class);

        removePotion(GhostInAJar.POTION_ID);

        ConsoleCommand.addCommand("reward", Reward.class);
    }

    @Override
    public void receiveEditCards() {
        final AbstractCard cards[] = {
                new LooseScrews(),
                new ZingZap(),
                new FusionRework(),
                new VentilationError(),
                new SQLInjection(),
                new Overcurrent(),
                new Refrigerate(),
                new Antivirus(),
                new LRUCache(),
                new CriticalSection(),
                new Fragmentation(),
                new Malware(),
                new RadarScan(),
                new SteadyAim(),
                new Centralize(),
                new StormRework(),
                new Overheat(),
                new GoForTheEyesRework(),
                new HotSockets(),
                new Biogeneration(),
                new Volatility(),
                new ThunderStrikeRework(),
                new NeutronShower(),
                new ChaosRework(),
                new SearingBlowRework(),
                new ImmolateRework(),
                new Hexaburn(),
                new CatalystRework(),
                new BiasedCognitionRework(),
                new LeapRework(),
                new StormOfSteelRework(),
                new ClawRework(),
                new AccuracyRework(),
                new ReflexRework(),
                new PhantasmalKillerRework(),
                new FollowUpRework(),
                new PreparedRework(),
                new SliceRework(),
                new FuseBlower(),
                new BlackIce()
        };

        final AbstractCard cardsBeta[] = {
                new DenialOfService(),
                new AutoShieldsRework(),
                new PropelledFlight(),
                new PusherProps(),
                new MetalCoating(),
                new DefragmentRework(),
                new Shadowmeld()
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

        BaseMod.addRelic(new EmptyCageRework(), RelicType.SHARED);
        BaseMod.addRelic(new CrimsonLotus(), RelicType.PURPLE);

        UnlockTracker.addRelic(EmptyCageRework.ID);
        UnlockTracker.addRelic(CrimsonLotus.ID);
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

    public static class CardChanges {
        public String ID = null;
        public int COST = -1;
        public AbstractCard.CardColor COLOR = null;
        public AbstractCard.CardRarity RARITY = null;

        public CardChanges(String ID, int COST, AbstractCard.CardColor COLOR, AbstractCard.CardRarity RARITY) {
            this.ID = ID;
            this.COST = COST;
            this.COLOR = COLOR;
            this.RARITY = RARITY;
        }
    }

    public static class RelicChanges {
        public String ID = null;
        public AbstractRelic.RelicTier tier = null;

        public RelicChanges(String ID, AbstractRelic.RelicTier tier) {
            this.ID = ID;
            this.tier = tier;
        }
    }
}