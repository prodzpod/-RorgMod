package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardBoilerplate extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:";

    public CardBoilerplate() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new CardBoilerplate();
    }
}
