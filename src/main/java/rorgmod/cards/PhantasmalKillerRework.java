package rorgmod.cards;

import com.megacrit.cardcrawl.cards.green.PhantasmalKiller;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PhantasmalPower;
import rorgmod.powers.PhantasmalPlusPower;

public class PhantasmalKillerRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Phantasmal Killer";

    public PhantasmalKillerRework() {
        super(ID, "rorgmod/cards/phantasmal_killer.png", 1, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        setCostUpgrade();
        REWORK_ID = PhantasmalKiller.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new PhantasmalPower(player, 1));
        applySelfPower(new PhantasmalPlusPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new PhantasmalKillerRework();
    }
}
