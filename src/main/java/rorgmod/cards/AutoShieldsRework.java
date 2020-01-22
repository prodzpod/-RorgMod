package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.AutoShieldsPower;

public class AutoShieldsRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Auto-Shields";

    public AutoShieldsRework() {
        super(CARD_ID, "rorgmod/cards/auto_shields.png", 1, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new AutoShieldsPower(player, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new AutoShieldsRework();
    }
}
