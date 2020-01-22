package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.DefragmentPower;

public class DefragmentRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Defragment";

    public DefragmentRework() {
        super(CARD_ID, "rorgmod/cards/defragment.png", 1, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1, 0);
        setCostUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new DefragmentPower(player, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new DefragmentRework();
    }
}
