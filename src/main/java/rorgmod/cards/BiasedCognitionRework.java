package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BiasPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class BiasedCognitionRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Biased Cognition";

    public BiasedCognitionRework() {
        super(CARD_ID, "rorgmod/cards/biased_cognition.png", 1, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        setMagic(3, 1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new FocusPower(player, magicNumber));
        applySelfPower(new BiasPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new BiasedCognitionRework();
    }
}
