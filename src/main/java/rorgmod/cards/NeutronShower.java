package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.actions.NeutronShowerAction;

public class NeutronShower extends AbstractRorgCard {

    public static final String ID = "rorgmod:Neutron Shower";

    public NeutronShower() {
        super(ID, DEFAULT_IMG_PATH_SKILL, 1, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        setExhaust();
        setNonExhaustUponUpgrade();
        setCustomUpgrade();
        BETA = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToTop(new NeutronShowerAction(player));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new NeutronShower();
    }
}
