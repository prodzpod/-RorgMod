package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.StormPower;

public class Storm extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Storm";

    public Storm() {
        super(CARD_ID, "rorgmod/cards/storm.png", 1, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setCostUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new StormPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Storm();
    }
}
