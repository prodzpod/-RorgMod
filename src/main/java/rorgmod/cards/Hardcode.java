package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.HardcodePower;
import rorgmod.powers.LockdownPower;

public class Hardcode extends AbstractRorgCard {

    public static final String ID = "rorgmod:Hardcode";

    public Hardcode() {
        super(ID, DEFAULT_IMG_PATH_SKILL, 2, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, 1);
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new HardcodePower(player, magicNumber));
        applySelfPower(new LockdownPower(player, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Hardcode();
    }
}
