package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Refrigerate extends AbstractRorgCard {

    public static final String ID = "rorgmod:Refrigerate";

    public Refrigerate() {
        super(ID, "rorgmod/cards/Refridgerate.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applyPower(monster, new WeakPower(monster, magicNumber, false));
        channelOrb(OrbType.FROST, 1);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Refrigerate();
    }
}
