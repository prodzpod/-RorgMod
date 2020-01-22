package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PreparedRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Prepared";

    public PreparedRework() {
        super(CARD_ID, "rorgmod/cards/prepared.png", 0, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.SELF);
        setMagic(1, 1);
        setCustomUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DrawCardAction(player, magicNumber + 1));
        addToBot(new DiscardAction(player, player, magicNumber, false));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new PreparedRework();
    }
}
