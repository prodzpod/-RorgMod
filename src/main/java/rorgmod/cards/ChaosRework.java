package rorgmod.cards;

import com.megacrit.cardcrawl.cards.blue.Chaos;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChaosRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Chaos";

    public ChaosRework() {
        super(ID, "rorgmod/cards/chaos.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setExhaust();
        setNonExhaustUponUpgrade();
        setCustomUpgrade();
        REWORK_ID = Chaos.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        channelOrb(OrbType.RANDOM, 2);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new ChaosRework();
    }
}
