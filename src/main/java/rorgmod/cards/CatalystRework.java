package rorgmod.cards;

import com.megacrit.cardcrawl.actions.unique.DoublePoisonAction;
import com.megacrit.cardcrawl.cards.green.Catalyst;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CatalystRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Catalyst";

    public CatalystRework() {
        super(ID, "rorgmod/cards/catalyst.png", 2, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setCostUpgrade();
        setExhaust();
        REWORK_ID = Catalyst.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new DoublePoisonAction(monster, player));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new CatalystRework();
    }
}
