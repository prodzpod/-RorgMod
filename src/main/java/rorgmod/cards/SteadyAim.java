package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class SteadyAim extends AbstractRorgCard {

    public static final String ID = "rorgmod:Steady Aim";

    public SteadyAim() {
        super(ID, "rorgmod/cards/SteadyAim.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        setBlock(4, 1);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        block(block, 2);
        applyPower(monster, new LockOnPower(monster, 1), 2);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new SteadyAim();
    }
}
