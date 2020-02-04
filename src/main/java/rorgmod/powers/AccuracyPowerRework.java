package rorgmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rorgmod.patches.CustomTriggerPatches;

public class AccuracyPowerRework extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Accuracy";

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
                if (!CustomTriggerPatches.AccuracyFix.accuracyHitting.get(c))
                    updateExisting(c, amount);
          }
    }

    private void updateExisting(AbstractCard c, int amount) {
        if (c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1)) {
            c.baseDamage += amount;
            c.applyPowers();
        }
        CustomTriggerPatches.AccuracyFix.accuracyHitting.set(c, true);
    }

    private void updateCards(int amount) {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) updateExisting(c, amount);
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) updateExisting(c, amount);
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) updateExisting(c, amount);
        for (AbstractCard c : AbstractDungeon.player.hand.group) updateExisting(c, amount);
    }
}