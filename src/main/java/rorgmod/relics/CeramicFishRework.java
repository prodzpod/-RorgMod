package rorgmod.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CeramicFish;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class CeramicFishRework extends AbstractRorgRelic {
    public static String ID = "rorgmod:Ceramic Fish";
    public CeramicFishRework() {
        super(ID, "rorgmod/relics/ceramic_fish.png", -1, RelicTier.COMMON, LandingSound.FLAT);
        REWORK_ID = CeramicFish.ID;
    }

    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof MonsterRoomElite) {
            this.pulse = true;
            beginPulse();
        } else this.pulse = false;
    }

    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            flash();
            this.pulse = false;
        }
    }

    public void onRewardScreen() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            AbstractDungeon.getCurrRoom().addGoldToRewards(
                    Settings.isDailyRun ? 120 : AbstractDungeon.treasureRng.random(100, 140));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CeramicFishRework();
    }
}
