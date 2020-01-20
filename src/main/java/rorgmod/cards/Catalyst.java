package rorgmod.cards;

import com.megacrit.cardcrawl.actions.unique.DoublePoisonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Catalyst extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Catalyst";

    public Catalyst() {
        super(CARD_ID, "rorgmod/cards/catalyst.png", 2, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setCostUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new DoublePoisonAction(monster, player));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Catalyst();
    }
}
