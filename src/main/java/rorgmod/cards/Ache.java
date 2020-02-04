package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Ache extends AbstractRorgCard {

    public static final String ID = "rorgmod:Ache";

    public Ache() {
        super(ID, DEFAULT_IMG_PATH_SKILL, -2, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.SELF);
        setUnplayable();
        setRetain();
    }

    public void triggerWhenDrawn() { this.addToTop(new DrawCardAction(1)); }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}
    public void upgrade() {}

    @Override
    public AbstractRorgCard makeCopy() {
        return new Ache();
    }
}
