package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.StormOfSteelPlusPower;
import rorgmod.powers.StormOfSteelPower;

public class StormOfSteelRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Storm of Steel";

    public StormOfSteelRework() {
        super(CARD_ID, "rorgmod/cards/storm_of_steel.png", 2, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        setCustomUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (upgraded) applySelfPower(new StormOfSteelPlusPower(player,1));
        else applySelfPower(new StormOfSteelPower(player,1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new StormOfSteelRework();
    }
}
