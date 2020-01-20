package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Alchemize extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Alchemize";

    public Alchemize() {
        super(CARD_ID, "rorgmod/cards/alchemize.png", 1, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        setCostUpgrade();
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Alchemize();
    }
}
