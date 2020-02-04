package rorgmod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.WraithFormAltPower;

public class WraithFormAltRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Wraith Form 2";

    public WraithFormAltRework() {
        super(ID, "rorgmod/cards/wraith_form.png", 3, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
        setMagic(3, 1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new WraithFormAltPower(player, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new WraithFormAltRework();
    }
}
