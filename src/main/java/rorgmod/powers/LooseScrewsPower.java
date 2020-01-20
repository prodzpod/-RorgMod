package rorgmod.powers;

import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LooseScrewsPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Loose Screws";
    private static final String IMG_PATH = "rorgmod/powers/looseScrews";

    public LooseScrewsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, IMG_PATH, PowerType.BUFF, RorgPowerType.TICKDOWN_END, false, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.addToTop(new IncreaseMaxOrbAction(1));
            this.flash();
        }
    }
}
