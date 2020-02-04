package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class TendonSlice extends AbstractRorgCard {

    public static final String ID = "rorgmod:Tendon Slice";

    public TendonSlice() {
        super(ID, DEFAULT_IMG_PATH_ATTACK, 0, CardType.ATTACK, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY);
        setAttack(5, 0);
        setMagic(5, 3);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        dealDamage(monster, damage);
        if (monster.hasPower(WeakPower.POWER_ID)) dealDamage(monster, damage + magicNumber - 5);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = BLUE_BORDER_GLOW_COLOR;
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.hasPower(WeakPower.POWER_ID)) {
                this.glowColor = GOLD_BORDER_GLOW_COLOR;
                break;
            }
        }
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new TendonSlice();
    }
}
