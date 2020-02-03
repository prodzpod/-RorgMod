package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import javax.smartcardio.Card;

public class WraithFormPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Wraith Form";
    private int cardsAffectedThisTurn = 0;

    public WraithFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "rorgmod/powers/wraithForm", PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        /* 41 */     this.cardsAffectedThisTurn = 0;
        /*    */   }

    @Override
    public void onDiscardCard() {
        super.onDiscardCard();
        if (this.cardsAffectedThisTurn >= amount) return;
        this.flash();
        AbstractCard card = AbstractDungeon.player.discardPile.getTopCard().makeSameInstanceOf();
        card.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true), card.energyOnUse, true, true));
        this.cardsAffectedThisTurn++;
    }
}