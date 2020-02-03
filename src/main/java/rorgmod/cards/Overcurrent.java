package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.OvercurrentPlusPower;
import rorgmod.powers.OvercurrentPower;

public class Overcurrent extends AbstractRorgCard {

    public static final String ID = "rorgmod:Overcurrent";

    public Overcurrent() {
        super(ID, "rorgmod/cards/Overcurrent.png", 0, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        setMagic(3,0);
        setExhaust();
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
