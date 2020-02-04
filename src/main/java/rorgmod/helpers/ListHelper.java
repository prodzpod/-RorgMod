package rorgmod.helpers;

import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.cards.colorless.Discovery;
import com.megacrit.cardcrawl.cards.colorless.SadisticNature;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.cards.purple.FollowUp;
import com.megacrit.cardcrawl.cards.red.Immolate;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.cards.red.Sentinel;
import com.megacrit.cardcrawl.cards.red.Warcry;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.beyond.SecretPortal;
import com.megacrit.cardcrawl.events.city.ForgottenAltar;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.FungiBeast;
import com.megacrit.cardcrawl.potions.GhostInAJar;
import com.megacrit.cardcrawl.relics.*;
import rorgmod.cards.*;
import rorgmod.commands.Reward;
import rorgmod.events.GremlinWheelRework;
import rorgmod.monsters.ShelledParasiteRework;
import rorgmod.monsters.WheelGremlin;
import rorgmod.relics.*;

import java.util.ArrayList;

public class ListHelper {
    // cards
    public static ArrayList<Class<? extends AbstractRorgCard>> cardsToAdd      = new ArrayList<Class<? extends AbstractRorgCard>>() {{
        add(LooseScrews.class);
        add(ZingZap.class);
        add(FusionRework.class);
        add(VentilationError.class);
        add(SQLInjection.class);
        add(Overcurrent.class);
        add(Refrigerate.class);
        add(Antivirus.class);
        add(LRUCache.class);
        add(CriticalSection.class);
        add(Fragmentation.class);
        add(Malware.class);
        add(SteadyAim.class);
        add(Centralize.class);
        add(StormRework.class);
        add(HotSockets.class);
        add(Biogeneration.class);
        add(Volatility.class);
        add(ThunderStrikeRework.class);
        add(ChaosRework.class);
        add(SearingBlowRework.class);
        add(ImmolateRework.class);
        add(Hexaburn.class);
        add(CatalystRework.class);
        add(BiasedCognitionRework.class);
        add(LeapRework.class);
        add(StormOfSteelRework.class);
        add(AccuracyRework.class);
        add(ReflexRework.class);
        add(PhantasmalKillerRework.class);
        add(FollowUpRework.class);
        add(PreparedRework.class);
        add(SliceRework.class);
        add(FuseBlower.class);
        add(BlackIce.class);
        add(DenialOfService.class);
        add(AutoShieldsRework.class);
        add(PropelledFlight.class);
        add(PusherProps.class);
        add(MetalCoating.class);
        add(DefragmentRework.class);
        add(ClawRework.class);
        add(NeutronShower.class);
        add(GoForTheEyesRework.class);
        add(Overheat.class);
        add(RadarScan.class);
        add(Shadowmeld.class);
        add(ToolsOfTheTradeRework.class);
        add(SentinelRework.class);
        add(WraithFormRework.class);
        add(WraithFormAltRework.class);
        add(TendonSlice.class);
        add(InjuryRework.class);
        add(Ache.class);
        add(Feeble.class);
        add(Fever.class);
        add(Hardcode.class);
    }};
    public static ArrayList<CardChanges     > cardsToTweak    = new ArrayList<CardChanges>() {{
        add(new CardChanges(Reboot.ID,         -1, null, AbstractCard.CardRarity.UNCOMMON));
        add(new CardChanges(Alchemize.ID,      -1, AbstractCard.CardColor.COLORLESS, null));
        add(new CardChanges(SadisticNature.ID, -1, AbstractCard.CardColor.GREEN, null));
        add(new CardChanges(Envenom.ID,        1 , null, null));
        add(new CardChanges(Discovery.ID,      -1, null, AbstractCard.CardRarity.RARE));
        add(new CardChanges(InfiniteBlades.ID, 0 , null, null));
        add(new CardChanges(Decay.ID).setRedraw());
        add(new CardChanges(Doubt.ID).setRedraw());
        add(new CardChanges(Normality.ID).setRedraw());
        add(new CardChanges(Pain.ID).setRedraw());
        add(new CardChanges(Regret.ID).setRedraw());
        add(new CardChanges(Shame.ID).setRedraw());
    }};
    public static ArrayList<String> cardsToRemove   = new ArrayList<String>() {{
        add(Scrape.ID);
        add(Aggregate.ID);
        add(Fusion.ID);
        add(GoForTheEyes.ID);
        add(Storm.ID);
        add(ThunderStrike.ID);
        add(Chaos.ID);
        add(SearingBlow.ID);
        add(Immolate.ID);
        add(Warcry.ID);
        add(WraithForm.ID);
        add(CorpseExplosion.ID);
        add(SneakyStrike.ID);
        add(Catalyst.ID);
        add(Apparition.ID);
        add(Defragment.ID);
        add(BiasedCognition.ID);
        add(Leap.ID);
        add(RipAndTear.ID);
        add(BeamCell.ID);
        add(SteamBarrier.ID);
        add(AutoShields.ID);
        add(Blasphemy.ID);
        add(StormOfSteel.ID);
        add(Claw.ID);
        add(Accuracy.ID);
        add(Reflex.ID);
        add(PhantasmalKiller.ID);
        add(FollowUp.ID);
        add(Prepared.ID);
        add(Slice.ID);
        add(Tempest.ID);
        add(ToolsOfTheTrade.ID);
        add(Sentinel.ID);
        add(Injury.ID);
        add(Reprogram.ID);
        add(Tempest.ID);
    }};

    public static ArrayList<String          > cardsInBeta     = new ArrayList<String>();
    public static ArrayList<String          > cardsReworked   = new ArrayList<String>();
    public static ArrayList<String          > cardsTweaked    = new ArrayList<String>();

    // relics
    public static ArrayList<RelicAdds       > relicsToAdd     = new ArrayList<RelicAdds>() {{
        add(new RelicAdds(LooseGear.class, RelicType.BLUE));
        add(new RelicAdds(GlossyCoat.class, RelicType.BLUE));
        add(new RelicAdds(CoolantMedium.class, RelicType.BLUE));
        add(new RelicAdds(DataDiskRework.class, RelicType.BLUE));
        add(new RelicAdds(DecayingCore.class, RelicType.BLUE));
        add(new RelicAdds(CrookedMotherboard.class, RelicType.BLUE));
        add(new RelicAdds(LiquidMercury.class, RelicType.BLUE));
        add(new RelicAdds(EmptyCageRework.class, RelicType.SHARED));
        add(new RelicAdds(CrimsonLotus.class, RelicType.PURPLE));
        add(new RelicAdds(CeramicFishRework.class, RelicType.SHARED));
        add(new RelicAdds(BlackStarRework.class, RelicType.SHARED));
        add(new RelicAdds(BootRework.class, RelicType.SHARED));
//        add(new RelicAdds(ShortCircuit.class, RelicType.BLUE));
    }};
    public static ArrayList<RelicChanges    > relicsToTweak   = new ArrayList<RelicChanges>() {{
        add(new RelicChanges(MummifiedHand.ID, AbstractRelic.RelicTier.RARE));
        add(new RelicChanges(WristBlade.ID,    AbstractRelic.RelicTier.RARE));
        add(new RelicChanges(NinjaScroll.ID,   AbstractRelic.RelicTier.SHOP));
        add(new RelicChanges(Shovel.ID,        AbstractRelic.RelicTier.UNCOMMON));
    }};
    public static ArrayList<String> relicsToRemove  = new ArrayList<String>() {{
        add(DeadBranch.ID);
        add(EmptyCage.ID);
        add(TinyHouse.ID);
        add(Melange.ID);
        add(DarkstonePeriapt.ID);
        add(VioletLotus.ID);
        add(IncenseBurner.ID);
        add(TinyChest.ID);
        add(CeramicFish.ID);
        add(Matryoshka.ID);
        add(Boot.ID);
//        add(CrackedCore.ID);
//        add(FrozenCore.ID);
    }};

    public static ArrayList<String          > relicsInBeta    = new ArrayList<String>();
    public static ArrayList<String          > relicsReworked  = new ArrayList<String>();
    public static ArrayList<String          > relicsTweaked   = new ArrayList<String>();

    // potions
    public static ArrayList<CustomPotion    > potionsToAdd    = new ArrayList<CustomPotion>();
    public static ArrayList<String> potionsToRemove = new ArrayList<String>() {{
        add(GhostInAJar.POTION_ID);
    }};

    public static ArrayList<String          > potionsInBeta   = new ArrayList<String>();
    public static ArrayList<String          > potionsReworked = new ArrayList<String>();
    public static ArrayList<String          > potionsTweaked  = new ArrayList<String>();

    // events
    public static ArrayList<EventAdds       > eventsToAdd     = new ArrayList<EventAdds>() {{
        add(new EventAdds(GremlinWheelRework.ID, GremlinWheelRework.class));
    }};
    public static ArrayList<String          > eventsToRemove  = new ArrayList<String>() {{
        add(Ghosts.ID);
        add(GremlinWheelGame.ID);
        add(ForgottenAltar.ID);
        add(SecretPortal.ID);
    }};

    // monsters
    public static ArrayList<MonsterAdds     > monstersToAdd   = new ArrayList<MonsterAdds>() {{
        add(new MonsterAdds(WheelGremlin.ID, WheelGremlin::new));
        add(new MonsterAdds(ShelledParasiteRework.ID, ShelledParasiteRework::new));
    }};
    public static ArrayList<EncounterChanges> monstersToChange= new ArrayList<EncounterChanges>() {{
        add(new EncounterChanges(MonsterHelper.SHELL_PARASITE_ENC, new Encounter(ShelledParasiteRework.class)));
        add(new EncounterChanges(MonsterHelper.PARASITE_AND_FUNGUS, new Encounter(ShelledParasiteRework.class, -260.0F, 15.0F), new Encounter(FungiBeast.class, 120.0F, 0.0F)));
    }};

    public static ArrayList<CommandAdds     > commandsToAdd   = new ArrayList<CommandAdds>() {{
        add(new CommandAdds("reward", Reward.class));
    }};

    // class
    public static class RelicAdds {
        public RelicType type;
        public Class<? extends AbstractRorgRelic> relic;

        public RelicAdds(Class<? extends AbstractRorgRelic> relic, RelicType type) {
            this.relic = relic;
            this.type = type;
        }
    }
    public static class PostRelicAdds {
        public RelicType type;
        public AbstractRorgRelic relic;

        public PostRelicAdds(RelicAdds adds) throws IllegalAccessException, InstantiationException {
            this.relic = adds.relic.newInstance();
            this.type = adds.type;
        }
        public PostRelicAdds(AbstractRorgRelic relic, RelicType type) {
            this.relic = relic;
            this.type = type;
        }
    }

    public static class EventAdds {
        public String id;
        public Class<? extends AbstractEvent> event;

        public EventAdds(String id, Class<? extends AbstractEvent> event) {
            this.id = id;
            this.event = event;
        }
    }

    public static class MonsterAdds {
        public String id;
        public BaseMod.GetMonster getMonster;

        public MonsterAdds(String id, BaseMod.GetMonster getMonster) {
            this.id = id;
            this.getMonster = getMonster;
        }
    }

    public static class CommandAdds {
        public String key;
        public Class<? extends ConsoleCommand> command;

        public CommandAdds(String key, Class<? extends ConsoleCommand> command) {
            this.key = key;
            this.command = command;
        }
    }

    public static class CardChanges {
        public String ID;
        public int COST;
        public AbstractCard.CardColor COLOR;
        public AbstractCard.CardRarity RARITY;
        public boolean REDRAW_FOR_CURSES = false;
        public CardChanges(String ID) { this(ID, -1, null, null); }
        public CardChanges(String ID, int COST, AbstractCard.CardColor COLOR, AbstractCard.CardRarity RARITY) {
            this.ID = ID;
            this.COST = COST;
            this.COLOR = COLOR;
            this.RARITY = RARITY;
        }
        public CardChanges setRedraw() { this.REDRAW_FOR_CURSES = true; return this; }
    }

    public static class RelicChanges {
        public String ID;
        public AbstractRelic.RelicTier tier;
        public RelicChanges(String ID, AbstractRelic.RelicTier tier) {
            this.ID = ID;
            this.tier = tier;
        }
    }

    public static class EncounterChanges {
        public String ID;
        public Encounter[] monster;
        public EncounterChanges(String ID, Encounter... monster) {
            this.ID = ID;
            this.monster = monster;
        }
    }
    public static class Encounter {
        public Class<? extends AbstractMonster> monster;
        public float xpos;
        public float ypos;

        public Encounter(Class<? extends AbstractMonster> monster) { this(monster, -1f, -1f); }
        public Encounter(Class<? extends AbstractMonster> monster, float xpos, float ypos) {
            this.monster = monster;
            this.xpos = xpos;
            this.ypos = ypos;
        }
    }
}
