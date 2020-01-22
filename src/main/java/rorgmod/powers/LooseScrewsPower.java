package rorgmod.powers;

import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LooseScrewsPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Loose Screws";

    public LooseScrewsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "rorgmod/powers/looseScrews", PowerType.BUFF, RorgPowerType.TICKDOWN_END, false, owner, amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.addToTop(new IncreaseMaxOrbAction(1));
            this.flash();
        }
    }
}
