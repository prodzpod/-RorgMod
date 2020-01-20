package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BiasPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import javax.swing.text.Position;

public class BiasedCognition extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Biased Cognition";

    public BiasedCognition() {
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
        return new BiasedCognition();
    }
}
