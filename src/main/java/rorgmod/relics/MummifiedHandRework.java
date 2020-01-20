package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import rorgmod.RorgMod;

import java.util.ArrayList;
import java.util.Iterator;

public class MummifiedHandRework extends AbstractRorgRelic {
    public static String ID = "rorgmod:Mummified Hand";
    private static String IMG_PATH = "rorgmod/relics/mummifiedHand.png";
    public MummifiedHandRework() {
        super(ID, IMG_PATH, -1, RelicTier.RARE, LandingSound.FLAT);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            ArrayList<AbstractCard> groupCopy = new ArrayList();
            Iterator var4 = AbstractDungeon.player.hand.group.iterator();

            while(true) {
                while(var4.hasNext()) {
                    AbstractCard c = (AbstractCard)var4.next();
                    if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                        groupCopy.add(c);
                    } else {
                        RorgMod.logger.info("COST IS 0: " + c.name);
                    }
                }

                var4 = AbstractDungeon.actionManager.cardQueue.iterator();

                while(var4.hasNext()) {
                    CardQueueItem i = (CardQueueItem)var4.next();
                    if (i.card != null) {
                        RorgMod.logger.info("INVALID: " + i.card.name);
                        groupCopy.remove(i.card);
                    }
                }

                AbstractCard c = null;
                if (groupCopy.isEmpty()) {
                    RorgMod.logger.info("NO VALID CARDS");
                } else {
                    RorgMod.logger.info("VALID CARDS: ");
                    Iterator var9 = groupCopy.iterator();

                    while(var9.hasNext()) {
                        AbstractCard cc = (AbstractCard)var9.next();
                        RorgMod.logger.info(cc.name);
                    }

                    c = (AbstractCard)groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
                }

                if (c != null) {
                    RorgMod.logger.info("Mummified hand: " + c.name);
                    c.setCostForTurn(0);
                } else {
                    RorgMod.logger.info("ERROR: MUMMIFIED HAND NOT WORKING");
                }
                break;
            }
        }

    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new MummifiedHandRework();
    }
}
