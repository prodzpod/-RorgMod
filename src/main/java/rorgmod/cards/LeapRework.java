package rorgmod.cards;

import com.megacrit.cardcrawl.cards.blue.Leap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class LeapRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Leap";

    public LeapRework() {
        super(ID, "rorgmod/cards/leap.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        setBlock(9, 3);
        REWORK_ID = Leap.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        block(block);
        applySelfPower(new DrawCardNextTurnPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new LeapRework();
    }
}
