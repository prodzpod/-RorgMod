package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import rorgmod.actions.ZingZapAction;

public class ZingZap extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Zing-Zap";

    public ZingZap() {
        super(CARD_ID, DEFAULT_IMG_PATH_ATTACK, 2, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        setAttack(10, 3);
        setBlock(3, 1);
    }

    public void applyPowers() {
        super.applyPowers();
        baseMagicNumber = 0;
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof Lightning) {
                baseMagicNumber += block;
            }
        }

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new ZingZapAction(this.block));
        dealDamage(monster, damage, AbstractGameAction.AttackEffect.LIGHTNING);
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new ZingZap();
    }
}
