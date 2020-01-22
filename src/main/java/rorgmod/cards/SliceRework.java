package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SliceRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Slice";

    public SliceRework() {
        super(CARD_ID, "rorgmod/cards/slice.png", 0, CardType.ATTACK, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY);
        setAttack(6, 3);
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
