package rorgmod.cards;

import com.megacrit.cardcrawl.cards.green.Accuracy;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.AccuracyPowerRework;

public class AccuracyRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Accuracy";

    public AccuracyRework() {
        super(ID, "rorgmod/cards/accuracy.png", 1, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3, 2);
        REWORK_ID = Accuracy.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new AccuracyPowerRework(player, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new AccuracyRework();
    }
}
