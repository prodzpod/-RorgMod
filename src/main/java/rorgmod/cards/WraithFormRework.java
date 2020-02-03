package rorgmod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.WraithFormPower;

public class WraithFormRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Wraith Form";

    public WraithFormRework() {
        super(ID, "rorgmod/cards/wraith_form.png", 3, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        setCostUpgrade();
        tags.add(BaseModCardTags.FORM);
        REWORK_ID = WraithForm.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new WraithFormPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new WraithFormRework();
    }
}
