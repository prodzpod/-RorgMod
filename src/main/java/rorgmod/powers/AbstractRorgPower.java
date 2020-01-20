package rorgmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rorgmod.RorgMod;

public class AbstractRorgPower extends AbstractPower {
    public static final String DEFAULT_IMG_PATH = "rorgmod/powers/default";
    private static int UNIQUE_ID = 0;

    public String POWER_ID = "rorgmod:Abstract Rorg Power";
    private PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public String NAME = powerStrings.NAME;
    public String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public RorgPowerType TYPE = RorgPowerType.TICKDOWN_END;

    public AbstractRorgPower(String id, String url, PowerType type, RorgPowerType type2, boolean isUnique, AbstractCreature owner) {
        this(id, url, type, type2, isUnique, owner, 1);
    }
    public AbstractRorgPower(String id, String url, PowerType type, RorgPowerType type2, boolean isUnique, AbstractCreature owner, int amount) {
        this.POWER_ID = id;
        this.powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        this.NAME = powerStrings.NAME;
        this.DESCRIPTIONS = powerStrings.DESCRIPTIONS;

        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(url + "_p.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(url + ".png"), 0, 0, 32, 32);
        this.type = type;
        this.canGoNegative = false;

        this.TYPE = type2;
        switch (this.TYPE) {
            case TICKDOWN_END:
            case TICKDOWN_START:
                this.isTurnBased = true;
                break;
            case ONETURN_END:
            case ONETURN_START:
            case GENERIC:
                this.isTurnBased = false;
                break;
            case NUMBERLESS:
            default:
                this.isTurnBased = false;
                this.amount = -1;
                break;
        }
        if (isUnique) {
            this.ID += "_" + UNIQUE_ID;
            UNIQUE_ID++;
        }

        this.updateDescription();
    }

    public void allowNegatives() {
        this.canGoNegative = true;
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.TYPE == RorgPowerType.NUMBERLESS) {
            RorgMod.logger.info(this.name + " does not stack");
        } else {
            this.fontScale = 8.0F;
            this.amount += stackAmount;

            if (this.amount >= 999) {
                this.amount = 999;
            }
            else if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        if (this.TYPE == RorgPowerType.NUMBERLESS) {
            RorgMod.logger.info(this.name + " does not stack");
        } else {
            this.fontScale = 8.0F;
            this.amount -= reduceAmount;

            if (!canGoNegative && amount < 0) {
                this.amount = 0;
            }
            if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
            else if (this.amount <= -999) {
                this.amount = -999;
            }
        }
    }

    public void endOfRound() { /** do something here instead of atEndOfRound **/ }

    @Override
    public void atEndOfRound() {
        endOfRound();
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            switch (this.TYPE) {
                case TICKDOWN_END:
                    this.flash();
                    this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
                    break;
                case ONETURN_END:
                    this.flash();
                    this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                    this.isTurnBased = true;
                    break;
                case TICKDOWN_START:
                case ONETURN_START:
                case GENERIC:
                case NUMBERLESS:
                default:
                    break;
            }
        }
    }

    public void endOfTurn() { /** do something here instead of atEndOfTurn **/ }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        endOfTurn();
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            switch (this.TYPE) {
                case TICKDOWN_START:
                    this.flash();
                    this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
                    break;
                case ONETURN_START:
                    this.flash();
                    this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                    this.isTurnBased = true;
                    break;
                case TICKDOWN_END:
                case ONETURN_END:
                case GENERIC:
                case NUMBERLESS:
                default:
                    break;
            }
        }
    }

    protected enum RorgPowerType {
        TICKDOWN_START,
        TICKDOWN_END,
        ONETURN_START,
        ONETURN_END,
        GENERIC,
        NUMBERLESS;

        private RorgPowerType() {}
    }
}
