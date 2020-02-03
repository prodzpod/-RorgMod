package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.VolatilityPower;

public class Volatility extends AbstractRorgCard {

    public static final String ID = "rorgmod:Volatility";

    public Volatility() {
        super(ID, DEFAULT_IMG_PATH_POWER, 1, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new VolatilityPower(player, magicNumber));
        channelOrb(OrbType.PLASMA, 1);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Volatility();
    }
}
