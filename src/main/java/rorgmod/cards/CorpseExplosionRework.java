package rorgmod.cards;

import com.megacrit.cardcrawl.cards.green.CorpseExplosion;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CorpseExplosionRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Corpse Explosion";

    public CorpseExplosionRework() {
        super(ID, "rorgmod/cards/corpse_explosion.png", 2, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        REWORK_ID = CorpseExplosion.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new CorpseExplosionRework();
    }
}
