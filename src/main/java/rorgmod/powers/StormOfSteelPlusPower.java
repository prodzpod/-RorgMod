package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class StormOfSteelPlusPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Storm of Steel+";

    public StormOfSteelPlusPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void onDiscardCard() {
        AbstractCard shiv = new Shiv();
        shiv.upgrade();
        addToBot(new MakeTempCardInHandAction(shiv));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}