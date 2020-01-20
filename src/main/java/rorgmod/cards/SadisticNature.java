package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SadisticPower;

public class SadisticNature extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Sadistic Nature";

    public SadisticNature() {
        super(CARD_ID, "rorgmod/cards/sadistic_nature.png", 1, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        setMagic(4, 2);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new SadisticPower(player, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new SadisticNature();
    }
}
