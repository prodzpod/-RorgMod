package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LRUCache extends AbstractRorgCard {

    public static final String ID = "rorgmod:LRU Cache";

    public LRUCache() {
        super(ID, DEFAULT_IMG_PATH_ATTACK, 1, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        setAttack(6,2);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealDamage(monster, damage, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        cycleOrbs(1);
        if      (player.filledOrbCount() >= 2) triggerPassives(1, magicNumber);
        else if (player.filledOrbCount() == 1) triggerPassives(0, magicNumber);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new LRUCache();
    }
}
