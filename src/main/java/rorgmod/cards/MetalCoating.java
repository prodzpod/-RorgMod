package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

public class MetalCoating extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Metal Coating";

    public MetalCoating() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(15, 3);
        setMagic(3, -1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        block(block);
        applySelfPower(new FrailPower(player, magicNumber, false));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new MetalCoating();
    }
}
