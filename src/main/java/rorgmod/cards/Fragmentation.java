package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fragmentation extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Fragmentation";

    public Fragmentation() {
        super(CARD_ID, DEFAULT_IMG_PATH_ATTACK, 1, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL);
        setAttack(5, 2);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealAOEDamage(damage, 2, AbstractGameAction.AttackEffect.SHIELD);
        loseOrbSlot(1);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Fragmentation();
    }
}
