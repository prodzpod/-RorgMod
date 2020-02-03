package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.ShadowmeldPower;

public class Shadowmeld extends AbstractRorgCard {

    public static final String ID = "rorgmod:Shadowmeld";

    public Shadowmeld() {
        super(ID, DEFAULT_IMG_PATH_POWER, 2, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        setCostUpgrade();
        BETA = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new ShadowmeldPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Shadowmeld();
    }
}
