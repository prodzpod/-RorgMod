package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DenialOfService extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Denial of Service";

    public DenialOfService() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 2, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setAttack(10, 3);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealDamage(monster, damage, monster.getIntentBaseDmg() >= 0 ? 2 : 1, AbstractGameAction.AttackEffect.POISON);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new DenialOfService();
    }
}
