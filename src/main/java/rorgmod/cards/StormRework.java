package rorgmod.cards;

import com.megacrit.cardcrawl.cards.blue.Storm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.StormPower;

public class StormRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Storm";

    public StormRework() {
        super(ID, "rorgmod/cards/storm.png", 1, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setCostUpgrade();
        REWORK_ID = Storm.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new StormPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new StormRework();
    }
}
