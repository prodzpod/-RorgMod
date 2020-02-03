package rorgmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ElectroPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import rorgmod.RorgMod;
import rorgmod.actions.ApplyAOEPowerAction;
import rorgmod.actions.CycleAction;
import rorgmod.actions.TriggerAllPassivesAction;
import rorgmod.actions.TriggerPassiveAction;

public class AbstractRorgCard extends CustomCard {

    protected static final String DEFAULT_ID = "rorgmod:Abstract Rorg Card";
    private static final CardStrings DEFAULT_CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(DEFAULT_ID);
    protected static final String DEFAULT_NAME = DEFAULT_CARD_STRINGS.NAME;
    protected static final String DEFAULT_DESCRIPTION = DEFAULT_CARD_STRINGS.DESCRIPTION;
    protected static final String DEFAULT_IMG_PATH_ATTACK = "rorgmod/cards/default_attack.png";
    protected static final String DEFAULT_IMG_PATH_SKILL = "rorgmod/cards/default_skill.png";
    protected static final String DEFAULT_IMG_PATH_POWER = "rorgmod/cards/default_power.png";
    protected static final int DEFAULT_COST = 0;
    protected static final CardType DEFAULT_TYPE = CardType.SKILL;
    protected static final CardColor DEFAULT_COLOR = CardColor.COLORLESS;
    protected static final CardRarity DEFAULT_RARITY = CardRarity.SPECIAL;
    protected static final CardTarget DEFAULT_TARGET = CardTarget.SELF;

    public String ID = DEFAULT_ID;
    protected CardStrings cardStrings = DEFAULT_CARD_STRINGS;
    public String NAME = DEFAULT_NAME;
    public String DESCRIPTION = DEFAULT_DESCRIPTION;
    public String IMG_PATH = DEFAULT_IMG_PATH_SKILL;
    public int COST = DEFAULT_COST;
    public CardType TYPE = DEFAULT_TYPE;
    public CardColor COLOR = DEFAULT_COLOR;
    public CardRarity RARITY = DEFAULT_RARITY;
    public CardTarget TARGET = DEFAULT_TARGET;

    public String REWORK_ID = null;
    public boolean BETA = false;

    public int ATTACK = 0;
    public int ATTACK_UPGRADE = 0;
    public int BLOCK = 0;
    public int BLOCK_UPGRADE = 0;
    public int MAGIC = 0;
    public int MAGIC_UPGRADE = 0;

    public boolean DO_COST_UPGRADE = false;
    public boolean CUSTOM_UPGRADE = false;
    public boolean NON_EXHAUST_UPON_UPGRADE = false;
    public boolean INNATE_UPON_UPGRADE = false;
    public boolean RETAIN_UPON_UPGRADE = false;

    public AbstractRorgCard() {
        this(DEFAULT_ID, DEFAULT_IMG_PATH_SKILL, DEFAULT_COST, DEFAULT_TYPE, DEFAULT_COLOR, DEFAULT_RARITY, DEFAULT_TARGET);
    }

    public AbstractRorgCard(String ID, String IMG_PATH, int COST, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, DEFAULT_NAME, IMG_PATH, COST, DEFAULT_DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.ID = ID;
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        this.NAME = cardStrings.NAME;
        this.DESCRIPTION = cardStrings.DESCRIPTION;
        this.IMG_PATH = IMG_PATH;
        this.COST = COST;
        this.TYPE = TYPE;
        this.COLOR = COLOR;
        this.RARITY = RARITY;
        this.TARGET = TARGET;

//        RorgMod.logger.info("Card Created, ID - NAME - DESC");
//        RorgMod.logger.info(this.ID);
//        RorgMod.logger.info(this.NAME);
//        RorgMod.logger.info(this.DESCRIPTION);

        this.originalName = this.NAME;
        this.name = this.NAME;
        this.rawDescription = this.DESCRIPTION;
        this.cost = this.COST;
        this.type = this.TYPE;
        this.color = this.COLOR;
        this.rarity = this.RARITY;
        this.target = this.TARGET;

        initializeTitle();
        initializeDescription();
    }

    public void setAttack(int ATTACK, int ATTACK_UPGRADE) {
        this.ATTACK = ATTACK;
        this.ATTACK_UPGRADE = ATTACK_UPGRADE;
        this.damage = this.baseDamage = this.ATTACK;
    }

    public void setBlock(int BLOCK, int BLOCK_UPGRADE) {
        this.BLOCK = BLOCK;
        this.BLOCK_UPGRADE = BLOCK_UPGRADE;
        this.block = this.baseBlock = this.BLOCK;
    }

    public void setMagic(int MAGIC, int MAGIC_UPGRADE) {
        this.MAGIC = MAGIC;
        this.MAGIC_UPGRADE = MAGIC_UPGRADE;
        this.magicNumber = this.baseMagicNumber = this.MAGIC;
    }

    public void setCostUpgrade() {
        this.DO_COST_UPGRADE = true;
    }

    public void setExhaust() {
        this.exhaust = true;
    }

    public void setEthereal() {
        this.isEthereal = true;
    }

    public void setUnplayable() {
        this.COST = -2;
        this.cost = -2;
        this.costForTurn = -2;
    }

    public void setInnate() { this.isInnate = true; }

    public void upgradeToInnate() { this.INNATE_UPON_UPGRADE = true; }

    public void setRetain() { this.selfRetain = true; }

    public void upgradeToRetain() { this.RETAIN_UPON_UPGRADE = true; }

    public void setCustomUpgrade() {
        this.CUSTOM_UPGRADE = true;
    }

    public void setNonExhaustUponUpgrade() {
        this.NON_EXHAUST_UPON_UPGRADE = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (ATTACK_UPGRADE != 0) upgradeDamage     (ATTACK_UPGRADE);
            if (BLOCK_UPGRADE  != 0) upgradeBlock      (BLOCK_UPGRADE);
            if (MAGIC_UPGRADE  != 0) upgradeMagicNumber(MAGIC_UPGRADE);
            if (DO_COST_UPGRADE)     upgradeBaseCost   (this.COST - 1);
            if (NON_EXHAUST_UPON_UPGRADE) this.exhaust    = false;
            if (INNATE_UPON_UPGRADE)      this.isInnate   = true;
            if (RETAIN_UPON_UPGRADE)      this.selfRetain = true;
            if (CUSTOM_UPGRADE) {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                this.initializeDescription();
            }
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        /*****************    OVERRIDE THIS   **************************************     PLEASE   **************/
        RorgMod.logger.error("Card use method is not overridden. Default card effect is played.");
        if (this.damage != 0) dealDamage(abstractMonster, damage);
        if (this.block != 0) block(block);
        if (this.magicNumber != 0) applyPower(abstractMonster, new VulnerablePower(abstractMonster, magicNumber, false));
    }

    // intents
    protected void dealDamage(AbstractCreature monster, int damage) { dealDamage(monster, damage, 1, AttackType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL); }
    protected void dealDamage(AbstractCreature monster, int damage, AttackType type) { dealDamage(monster, damage, 1, type); }
    protected void dealDamage(AbstractCreature monster, int damage, AbstractGameAction.AttackEffect effect) { dealDamage(monster, damage, 1, AttackType.NORMAL, effect); }
    protected void dealDamage(AbstractCreature monster, int damage, AttackType type, AbstractGameAction.AttackEffect effect) { dealDamage(monster, damage, 1, type, effect); }

    protected void dealDamage(AbstractCreature monster, int damage, int times) { dealDamage(monster, damage, times, AttackType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL); }
    protected void dealDamage(AbstractCreature monster, int damage, int times, AttackType type) {
        switch (type) {
            case NORMAL:
                dealDamage(monster, damage, times, type, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
                break;
            case HPLOSS:
                dealDamage(monster, damage, times, type, AbstractGameAction.AttackEffect.POISON);
                break;
            case THORNS:
                dealDamage(monster, damage, times, type, AbstractGameAction.AttackEffect.SHIELD);
                break;
            case LIGHTNING:
                dealDamage(monster, damage, times, type, AbstractGameAction.AttackEffect.LIGHTNING);
                break;
            case DARK:
                dealDamage(monster, damage, times, type, AbstractGameAction.AttackEffect.FIRE);
                break;
        }
    }
    protected void dealDamage(AbstractCreature monster, int damage, int times, AbstractGameAction.AttackEffect effect) { dealDamage(monster, damage, times, AttackType.NORMAL, effect); }
    protected void dealDamage(AbstractCreature monster, int damage, int times, AttackType type, AbstractGameAction.AttackEffect effect) {
        DamageInfo.DamageType dType;
        int output = damage;
        switch (type) {
            case NORMAL:
                dType = DamageInfo.DamageType.NORMAL;
                break;
            case HPLOSS:
                dType = DamageInfo.DamageType.HP_LOSS;
                break;
            case LIGHTNING:
            case DARK:
                output = AbstractOrb.applyLockOn(monster, damage);
            default: // THORNS
                dType = DamageInfo.DamageType.THORNS;
                break;
        }
        if (type == AttackType.LIGHTNING && AbstractDungeon.player.hasPower(ElectroPower.POWER_ID)) dealAOEDamage(damage, times, type, effect);
        else for (int i = 0; i < times; i++) this.addToBot(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, output, dType), effect));
    }

    protected void dealRandomDamage(int damage) { dealRandomDamage(damage, 1, AttackType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);}
    protected void dealRandomDamage(int damage, AttackType type) { dealRandomDamage(damage, 1, type); }
    protected void dealRandomDamage(int damage, AbstractGameAction.AttackEffect effect) { dealRandomDamage(damage, 1, AttackType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL); }
    protected void dealRandomDamage(int damage, AttackType type, AbstractGameAction.AttackEffect effect) { dealRandomDamage(damage, 1, type, effect);}
    protected void dealRandomDamage(int damage, int times) { dealRandomDamage(damage, times, AttackType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);}
    protected void dealRandomDamage(int damage, int times, AttackType type) {
        switch (type) {
            case NORMAL:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
                break;
            case HPLOSS:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.POISON);
                break;
            case THORNS:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.SHIELD);
                break;
            case LIGHTNING:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.LIGHTNING);
                break;
            case DARK:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.FIRE);
                break;
        }
    }
    protected void dealRandomDamage(int damage, int times, AbstractGameAction.AttackEffect effect) { dealRandomDamage(damage, times, AttackType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL); }
    protected void dealRandomDamage(int damage, int times, AttackType type, AbstractGameAction.AttackEffect effect) {
        DamageInfo.DamageType dType;
        boolean lockonDependency = false;
        switch (type) {
            case NORMAL:
                dType = DamageInfo.DamageType.NORMAL;
                break;
            case HPLOSS:
                dType = DamageInfo.DamageType.HP_LOSS;
                break;
            case LIGHTNING:
            case DARK:
                lockonDependency = true;
            default: // THORNS
                dType = DamageInfo.DamageType.THORNS;
                break;
        }
        if (type == AttackType.LIGHTNING && AbstractDungeon.player.hasPower(ElectroPower.POWER_ID)) dealAOEDamage(damage, times, type, effect);
        else for (int i = 0; i < times; i++) {
            AbstractCreature monster = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            this.addToBot(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, lockonDependency ? AbstractOrb.applyLockOn(monster, damage) : damage, dType), effect));
        }
    }

    protected void dealAOEDamage(int damage) { dealAOEDamage(damage, 1, AttackType.NORMAL, AbstractGameAction.AttackEffect.SMASH); }
    protected void dealAOEDamage(int damage, AttackType type) { dealAOEDamage(damage, 1, type); }
    protected void dealAOEDamage(int damage, AbstractGameAction.AttackEffect effect) { dealAOEDamage(damage, 1, AttackType.NORMAL, effect); }
    protected void dealAOEDamage(int damage, AttackType type, AbstractGameAction.AttackEffect effect) { dealAOEDamage(damage, 1, type, effect); }
    protected void dealAOEDamage(int damage, int times) { dealAOEDamage(damage, times, AttackType.NORMAL, AbstractGameAction.AttackEffect.SMASH); }
    protected void dealAOEDamage(int damage, int times, AttackType type) {
        switch (type) {
            case NORMAL:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.SMASH);
                break;
            case HPLOSS:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.POISON);
                break;
            case THORNS:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.SHIELD);
                break;
            case LIGHTNING:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.LIGHTNING);
                break;
            case DARK:
                dealRandomDamage(damage, times, type, AbstractGameAction.AttackEffect.FIRE);
                break;
        }
    }
    protected void dealAOEDamage(int damage, int times, AbstractGameAction.AttackEffect effect) { dealAOEDamage(damage, times, AttackType.NORMAL, effect); }
    protected void dealAOEDamage(int damage, int times, AttackType type, AbstractGameAction.AttackEffect effect) {
        DamageInfo.DamageType dType;
        boolean orbDamage = false;
        switch (type) {
            case NORMAL:
                dType = DamageInfo.DamageType.NORMAL;
                break;
            case HPLOSS:
                dType = DamageInfo.DamageType.HP_LOSS;
                break;
            case LIGHTNING:
            case DARK:
                orbDamage = true;
            default: // THORNS
                dType = DamageInfo.DamageType.THORNS;
                break;
        }
        for (int i = 0; i < times; i++) this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,
                DamageInfo.createDamageMatrix(damage, dType != DamageInfo.DamageType.NORMAL, orbDamage),
                dType, effect, true));
    }

    protected enum AttackType {
        NORMAL,
        HPLOSS,
        THORNS,
        LIGHTNING,
        DARK
    }

    protected void block(int block) { block(block, 1); }
    protected void block(int block, int times) {
        for (int i = 0; i < times; i++) this.addToBot(new GainBlockAction(AbstractDungeon.player, block));
    }

    protected void applySelfPower(AbstractPower power) { applyPower(AbstractDungeon.player, power, 1); }
    protected void applySelfPower(AbstractPower power, int times) { applyPower(AbstractDungeon.player, power, times); }

    protected void applyPower(AbstractCreature monster, AbstractPower power) { applyPower(monster, power, 1); }
    protected void applyPower(AbstractCreature monster, AbstractPower power, int times) {
        int stack = power.amount;
        for (int i = 0; i < times; i++) this.addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, power, stack, true, AbstractGameAction.AttackEffect.NONE));
        // power needs to be constructed outside function as parameters differs
    }

    protected void applyAOEPower(AbstractPower power) { applyAOEPower(power, 1); }
    protected void applyAOEPower(AbstractPower power, int times) {
        for (int i = 0; i < times; i++) this.addToBot(new ApplyAOEPowerAction(power));
    }

    protected void evokeOrb() { evokeOrb(1, 1); }
    protected void evokeOrb(int amount) { evokeOrb(amount, 1); }
    protected void evokeOrb(int amount, int times) {
        for (int i = 0; i < amount; i++) { // amount loop
            for (int j = 0; j < times - 1; j++) { // times loop
                this.addToBot(new AnimateOrbAction(1));
                this.addToBot(new EvokeWithoutRemovingOrbAction(1));
            }
            this.addToBot(new AnimateOrbAction(1));
            this.addToBot(new EvokeOrbAction(1));
        }
    }

    protected void gainOrbSlot(int amount) {
        this.addToBot(new IncreaseMaxOrbAction(amount));
    }

    protected void loseOrbSlot(int amount) {
        this.addToBot(new DecreaseMaxOrbAction(amount));
    }

    protected void channelOrb(OrbType orb) { channelOrb(orb, 1); }
    protected void channelOrb(OrbType orb, int amount) {
        OrbType currentOrb = orb;
        for (int i = 0; i < amount; i++) {
            if (orb == OrbType.RANDOM) currentOrb = getRandomOrb();
            switch (currentOrb) {
                case LIGHTNING:
                    this.addToBot(new ChannelAction(new Lightning()));
                    break;
                case FROST:
                    this.addToBot(new ChannelAction(new Frost()));
                    break;
                case DARK:
                    this.addToBot(new ChannelAction(new Dark()));
                    break;
                case PLASMA:
                default:
                    this.addToBot(new ChannelAction(new Plasma()));
                    break;
            }
        }
    }

    private OrbType getRandomOrb() {
        int random = AbstractDungeon.miscRng.random(0, 3);
        switch (random) {
            case 0:
                return OrbType.LIGHTNING;
            case 1:
                return OrbType.FROST;
            case 2:
                return OrbType.DARK;
            case 3:
            default:
                return OrbType.PLASMA;
        }
    }

    protected void triggerPassives() { triggerPassives(1); }
    protected void triggerPassives(int number) { this.addToBot(new TriggerPassiveAction(number)); }
    protected void triggerPassives(int number, int number2) { this.addToBot(new TriggerPassiveAction(number, number2)); }
    protected void triggerPassives(AbstractOrb orb) { triggerPassives(orb, 1); }
    protected void triggerPassives(AbstractOrb orb, int number) {
        this.addToBot(new TriggerPassiveAction(orb, number));
    }

    protected void triggerAllPassives() {
        this.addToBot(new TriggerAllPassivesAction());
    }

    protected void cycleOrbs(int amount) {
        for (int i = 0; i < amount; i++)
            this.addToTop(new CycleAction());
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new AbstractRorgCard();
    }

    public static enum OrbType {
        LIGHTNING,
        FROST,
        DARK,
        PLASMA,
        RANDOM;

        private OrbType() {
        }
    }
}
