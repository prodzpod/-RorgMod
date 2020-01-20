package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.OvercurrentPlusPower;
import rorgmod.powers.OvercurrentPower;

public class Overcurrent extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Overcurrent";

    public Overcurrent() {
        super("rorgmod:Overcurrent", DEFAULT_IMG_PATH_SKILL, 0, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        setMagic(3,0);
        setCustomUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        gainOrbSlot(3);
        if (this.upgraded) applySelfPower(new OvercurrentPlusPower(player, 3));
        applySelfPower(new OvercurrentPower(player, 3));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Overcurrent();
    }
}
