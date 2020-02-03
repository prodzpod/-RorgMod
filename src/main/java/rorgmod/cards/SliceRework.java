package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.green.Slice;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SliceRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Slice";

    public SliceRework() {
        super(ID, "rorgmod/cards/slice.png", 0, CardType.ATTACK, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY);
        setAttack(6, 3);
        REWORK_ID = Slice.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealDamage(monster, damage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new SliceRework();
    }
}
