package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class HotSockets extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Hot Sockets";

    public HotSockets() {
        super(CARD_ID, DEFAULT_IMG_PATH_ATTACK, 1, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setAttack(4,1);
    }

    public void applyPowers() {
        super.applyPowers();
        baseMagicNumber = damage;
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                baseMagicNumber += damage;
            }
        }

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        evokeOrb(1, 1);
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                dealRandomDamage(damage, AbstractGameAction.AttackEffect.LIGHTNING);
            }
        }
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new HotSockets();
    }
}
