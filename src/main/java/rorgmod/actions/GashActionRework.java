package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rorgmod.cards.ClawRework;

public class GashActionRework extends AbstractGameAction {


    private AbstractCard card;

    public GashActionRework(AbstractCard card, int amount) {
        this.actionType = ActionType.SPECIAL;
        this.card = card;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (card != null) {
            card.baseDamage += amount;
            card.applyPowers();
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            updateCards(c);
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            updateCards(c);
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            updateCards(c);

        this.isDone = true;
    }

    private void updateCards(AbstractCard c) {
        if (c instanceof ClawRework) {
            c.baseDamage += this.amount;
            c.applyPowers();
        }
    }
}
