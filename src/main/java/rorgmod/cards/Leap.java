package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class Leap extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Leap";

    public Leap() {
        super(CARD_ID, "rorgmod/cards/leap.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        setBlock(9, 3);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        block(block);
        applySelfPower(new DrawCardNextTurnPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Leap();
    }
}
