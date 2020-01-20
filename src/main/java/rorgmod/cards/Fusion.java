package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fusion extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Fusion";

    public Fusion() {
        super(CARD_ID, "rorgmod/cards/fusion.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setCustomUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (this.upgraded) gainOrbSlot(1);
        channelOrb(OrbType.PLASMA, 1);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Fusion();
    }
}
