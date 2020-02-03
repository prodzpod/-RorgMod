package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DataDisk;

public class DataDiskRework extends AbstractRorgRelic {
    public static String ID = "rorgmod:Data Disk";
    public DataDiskRework() {
        super(ID, "rorgmod/relics/dataDisk.png", -1, RelicTier.UNCOMMON, LandingSound.FLAT);
        REWORK_ID = DataDisk.ID;
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
