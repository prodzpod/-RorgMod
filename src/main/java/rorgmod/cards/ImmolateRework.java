package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ImmolateRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Immolate";

    public ImmolateRework() {
        super(CARD_ID, "rorgmod/cards/immolate.png", 2, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ALL);
        setAttack(20, 6);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealAOEDamage(damage, AbstractGameAction.AttackEffect.FIRE);
        this.addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, true, false));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new ImmolateRework();
    }
}
