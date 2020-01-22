package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.GoForTheEyesPower;

public class GoForTheEyesRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Go for the Eyes";

    public GoForTheEyesRework() {
        super(CARD_ID, "rorgmod/cards/go_for_the_eyes.png", 1, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1, 1);
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
