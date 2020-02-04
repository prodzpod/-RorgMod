package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class Feeble extends AbstractRorgCard {

    public static final String ID = "rorgmod:Feeble";

    public Feeble() {
        super(ID, DEFAULT_IMG_PATH_SKILL, -2, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.SELF);
        setUnplayable();
    }

    public void triggerWhenDrawn() {
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
        this.addToTop(new DrawCardAction(1));
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}
    public void upgrade() {}

    @Override
    public AbstractRorgCard makeCopy() {
        return new Feeble();
    }
}
