package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class VentilationError extends AbstractRorgCard {

    public static final String ID = "rorgmod:Ventilation Error";

    public VentilationError() {
        super("rorgmod:Ventilation Error", DEFAULT_IMG_PATH_SKILL, 2, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL);
        setMagic(3,2);
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        evokeOrb(1,2);
        applyAOEPower(new WeakPower(null, this.magicNumber, false));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new VentilationError();
    }
}
