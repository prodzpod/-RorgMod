package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;

public class InjuryRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Injury";

    public InjuryRework() {
        super(ID, "rorgmod/cards/injury.png", -2, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        setUnplayable();
    }

    public void triggerWhenDrawn() {
        this.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ConstrictedPower(AbstractDungeon.player, AbstractDungeon.player, 5)));
        this.addToTop(new DrawCardAction(1));
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}
    public void upgrade() {}

    @Override
    public AbstractRorgCard makeCopy() {
        return new InjuryRework();
    }
}
