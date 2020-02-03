package rorgmod.cards;

import com.megacrit.cardcrawl.cards.blue.Fusion;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FusionRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Fusion";

    public FusionRework() {
        super(ID, "rorgmod/cards/fusion.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setExhaust();
        setCustomUpgrade();
        REWORK_ID = Fusion.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (this.upgraded) gainOrbSlot(1);
        channelOrb(OrbType.PLASMA, 1);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new FusionRework();
    }
}
