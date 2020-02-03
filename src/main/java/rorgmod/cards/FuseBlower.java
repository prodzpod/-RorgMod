package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FuseBlower extends AbstractRorgCard {

    public static final String ID = "rorgmod:Fuse Blower";

    public FuseBlower() {
        super(ID, DEFAULT_IMG_PATH_SKILL, 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setCostUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        channelOrb(OrbType.LIGHTNING, 2);
        this.addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, true, false));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new FuseBlower();
    }
}
