package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BootRework extends AbstractRorgRelic {
    public static String ID = "rorgmod:The Boot";
    private static final int BOOT_DAMAGE = 6;
    public BootRework() {
        super(ID, "rorgmod/relics/boot.png", -1, RelicTier.COMMON, LandingSound.HEAVY);
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0 && damageAmount < BOOT_DAMAGE) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return BOOT_DAMAGE;
        }
        return damageAmount;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BootRework();
    }
}
