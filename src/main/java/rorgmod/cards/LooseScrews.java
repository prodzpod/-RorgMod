package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.powers.LooseScrewsPower;

public class LooseScrews extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Loose Screws";

    public LooseScrews() {
        super("rorgmod:Loose Screws", DEFAULT_IMG_PATH_ATTACK, 2, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF_AND_ENEMY);
        setAttack(12, 4);
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
