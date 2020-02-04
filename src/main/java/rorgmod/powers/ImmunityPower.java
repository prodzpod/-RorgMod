package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class ImmunityPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Immunity";

    public ImmunityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
        allowZero();
    }

    public void endOfRound() {
        this.amount = 0;
        updateDescription();
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK)
            addToBot(new ApplyPowerAction(this.owner, this.owner, new ImmunityPower(this.owner, 1), 1));
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (1.0F - Math.min(this.amount, 10) * 0.1F);
        }
        return damage;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];
        if (this.amount != 0) {
            this.description += DESCRIPTIONS[2] + ((10 - Math.min(this.amount, 10)) * 10) + DESCRIPTIONS[3];
        }
    }
}