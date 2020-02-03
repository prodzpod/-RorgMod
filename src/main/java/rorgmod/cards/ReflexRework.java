package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReflexRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Reflex";

    public ReflexRework() {
        super(ID, "rorgmod/cards/reflex.png", -2, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, 0);
        setUnplayable();
        setExhaust();
        setNonExhaustUponUpgrade();
        setCustomUpgrade();
        REWORK_ID = Reflex.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DrawCardAction(player, this.magicNumber));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void triggerOnManualDiscard() {
        addToBot(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        if (!upgraded) addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new ReflexRework();
    }
}
