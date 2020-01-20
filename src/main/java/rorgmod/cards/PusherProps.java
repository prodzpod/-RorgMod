package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class PusherProps extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Pusher Props";

    public PusherProps() {
        super(CARD_ID, DEFAULT_IMG_PATH_ATTACK, 2, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setAttack(8, 2);
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealDamage(monster, damage, 2, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        AbstractPower power = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        if (power != null && power.amount >= 2)
            applyPower(monster, new WeakPower(monster, magicNumber, false));
    }

    public void triggerOnGlowCheck() {
        AbstractPower power = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
        this.glowColor = power != null && power.amount >= 2 ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new PusherProps();
    }
}
