package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class AccuracyPowerRework extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Accuracy";
    private ArrayList<AbstractCard> tracked = new ArrayList<>();

    public AccuracyPowerRework(AbstractCreature owner, int amount) {
        super(POWER_ID, "rorgmod/powers/accuracy", PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
        updateCards(amount);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateCards(stackAmount);
    }

    public void onDrawOrDiscard() {
          for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!tracked.contains(c)) updateExisting(c, amount);
          }
    }

    private void updateExisting(AbstractCard c, int amount) {
        if (c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1)) {
            c.baseDamage += amount;
            c.applyPowers();
        }
        tracked.add(c);
    }

    private void updateCards(int amount) {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) updateExisting(c, amount);
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) updateExisting(c, amount);
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) updateExisting(c, amount);
        for (AbstractCard c : AbstractDungeon.player.hand.group) updateExisting(c, amount);
    }
}