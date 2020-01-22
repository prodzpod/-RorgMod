package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class GashPlusAction extends AbstractGameAction {


    private AbstractCard card;

    public GashPlusAction(AbstractCard card, int amount) {
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
        if (c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1)) {
            c.baseDamage += this.amount;
            c.applyPowers();
        }
    }
}
