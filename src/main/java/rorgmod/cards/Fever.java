package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fever extends AbstractRorgCard {

    public static final String ID = "rorgmod:Fever";

    public Fever() {
        super(ID, DEFAULT_IMG_PATH_SKILL, -2, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.SELF);
        setUnplayable();
    }

    public void triggerWhenDrawn() { this.addToTop(new DrawCardAction(1)); }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.costForTurn > 0) dealDamage(AbstractDungeon.player, c.costForTurn, AttackType.HPLOSS, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}
    public void upgrade() {}

    @Override
    public AbstractRorgCard makeCopy() {
        return new Fever();
    }
}
