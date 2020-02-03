package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.LooseScrewsPower;

public class LooseScrews extends AbstractRorgCard {

    public static final String ID = "rorgmod:Loose Screws";

    public LooseScrews() {
        super(ID, "rorgmod/cards/loose_screws.png", 2, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF_AND_ENEMY);
        setAttack(12, 4);
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealDamage(monster, damage, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        applySelfPower(new LooseScrewsPower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new LooseScrews();
    }
}
