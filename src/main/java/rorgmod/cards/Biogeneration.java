package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Biogeneration extends AbstractRorgCard {

    public static final String ID = "rorgmod:Biogeneration";

    public Biogeneration() {
        super(ID, DEFAULT_IMG_PATH_ATTACK, 2, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ALL);
        setAttack(16, 4);
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealAOEDamage(damage, AbstractGameAction.AttackEffect.FIRE);
        channelOrb(OrbType.PLASMA, 1);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Biogeneration();
    }
}
