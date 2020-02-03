package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.CentralizePower;

public class Centralize extends AbstractRorgCard {

    public static final String ID = "rorgmod:Centralize";

    public Centralize() {
        super(ID, "rorgmod/cards/Centralize.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new CentralizePower(player, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Centralize();
    }
}
