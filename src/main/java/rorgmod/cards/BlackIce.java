package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.BlackIcePower;

public class BlackIce extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Black Ice";

    public BlackIce() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 2, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        setCostUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new BlackIcePower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new BlackIce();
    }
}
