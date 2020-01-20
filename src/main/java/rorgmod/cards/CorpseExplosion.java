package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CorpseExplosion extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Corpse Explosion";

    public CorpseExplosion() {
        super(CARD_ID, "rorgmod/cards/corpse_explosion.png", 2, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new CorpseExplosion();
    }
}
