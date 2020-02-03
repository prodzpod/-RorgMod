package rorgmod.cards;

import com.megacrit.cardcrawl.cards.blue.AutoShields;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.AutoShieldsPower;

public class AutoShieldsRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Auto-Shields";

    public AutoShieldsRework() {
        super(ID, "rorgmod/cards/auto_shields.png", 1, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        setMagic(2, 1);
        REWORK_ID = AutoShields.ID;
        BETA = true;
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
