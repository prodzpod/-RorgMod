package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class HotSockets extends AbstractRorgCard {

    public static final String ID = "rorgmod:Hot Sockets";

    public HotSockets() {
        super(ID, DEFAULT_IMG_PATH_ATTACK, 1, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL);
        setAttack(4,2);
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.filledOrbCount() != 0) baseMagicNumber = 1;
        else baseMagicNumber = 0;
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                baseMagicNumber++;
            }
        }
        magicNumber = baseMagicNumber;

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        evokeOrb(1, 1);
        dealRandomDamage(damage, magicNumber, AbstractGameAction.AttackEffect.LIGHTNING);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new HotSockets();
    }
}
