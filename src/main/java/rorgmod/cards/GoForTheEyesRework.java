package rorgmod.cards;

import com.megacrit.cardcrawl.cards.blue.GoForTheEyes;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.GoForTheEyesPower;

public class GoForTheEyesRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Go for the Eyes";

    public GoForTheEyesRework() {
        super(ID, "rorgmod/cards/go_for_the_eyes.png", 1, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1, 1);
        REWORK_ID = GoForTheEyes.ID;
        BETA = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new GoForTheEyesPower(player, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new GoForTheEyesRework();
    }
}
