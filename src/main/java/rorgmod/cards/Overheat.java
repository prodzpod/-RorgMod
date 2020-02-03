package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.OverheatPower;

public class Overheat extends AbstractRorgCard {

    public static final String ID = "rorgmod:Overheat";

    public Overheat() {
        super(ID, DEFAULT_IMG_PATH_SKILL, 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setMagic(1, 1);
        BETA = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applyPower(monster, new OverheatPower(monster, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Overheat();
    }
}
