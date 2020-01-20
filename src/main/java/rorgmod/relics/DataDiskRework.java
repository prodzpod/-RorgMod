package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DataDiskRework extends AbstractRorgRelic {
    public static String ID = "rorgmod:Data Disk";
    private static String IMG_PATH = "rorgmod/relics/dataDisk.png";
    public DataDiskRework() {
        super(ID, IMG_PATH, -1, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1)));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DataDiskRework();
    }
}
