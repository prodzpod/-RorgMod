package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Hexaburn extends AbstractRorgCard {

    public static final String ID = "rorgmod:Hexaburn";

    public Hexaburn() {
        super(ID, "rorgmod/cards/burn.png", -2, CardType.STATUS, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        setMagic(4, 0);
        setUnplayable();
    }

    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (this.dontTriggerOnUseCard) {
            dealDamage(player, magicNumber, AttackType.THORNS, AbstractGameAction.AttackEffect.FIRE);
        }
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public void upgrade() {
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Hexaburn();
    }
}
