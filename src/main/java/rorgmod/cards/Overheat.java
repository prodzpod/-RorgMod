package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.OverheatPower;

public class Overheat extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Overheat";

    public Overheat() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 2, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setCostUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applyPower(monster, new OverheatPower(monster, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Overheat();
    }
}
