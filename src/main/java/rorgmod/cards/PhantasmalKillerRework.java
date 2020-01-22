package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PhantasmalPower;
import rorgmod.powers.PhantasmalPlusPower;

public class PhantasmalKillerRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Phantasmal Killer";

    public PhantasmalKillerRework() {
        super(CARD_ID, "rorgmod/cards/phantasmal_killer.png", 1, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        setCustomUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new PhantasmalPower(player, 1));
        if (upgraded) applySelfPower(new PhantasmalPlusPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new PhantasmalKillerRework();
    }
}
