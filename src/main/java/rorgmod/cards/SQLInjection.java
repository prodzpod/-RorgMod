package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class SQLInjection extends AbstractRorgCard {

    public static final String ID = "rorgmod:SQL Injection";

    public SQLInjection() {
        super("rorgmod:SQL Injection", DEFAULT_IMG_PATH_ATTACK, 2, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        setAttack(8, 3);
        setMagic(2, 1);
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealDamage(monster, damage, AbstractGameAction.AttackEffect.POISON);
        if (AbstractDungeon.actionManager.orbsChanneledThisTurn.size() >= 2)
            applyPower(monster, new VulnerablePower(monster, magicNumber, false));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDungeon.actionManager.orbsChanneledThisTurn.size() >= 2 ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new SQLInjection();
    }
}
