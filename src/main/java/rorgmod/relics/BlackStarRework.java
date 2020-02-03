package rorgmod.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlackStar;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import rorgmod.RorgMod;

import java.util.ArrayList;

public class BlackStarRework extends AbstractRorgRelic {
    public static String ID = "rorgmod:Black Star";
    public BlackStarRework() {
        super(ID, "rorgmod/relics/blackstar.png", -1, RelicTier.BOSS, LandingSound.HEAVY);
        REWORK_ID = BlackStar.ID;
    }

    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof MonsterRoomElite) {
            this.pulse = true;
            beginPulse();
            if (!CardCrawlGame.loadingSave) AbstractDungeon.getCurrRoom().addGoldToRewards(Settings.isDailyRun ? 120 : AbstractDungeon.treasureRng.random(100, 140));
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
            // Doubling up!!!!
//            RorgMod.logger.info("black star debug");
            for (RewardItem item : AbstractDungeon.getCurrRoom().rewards) RorgMod.logger.info(item);
            for (RewardItem item : (ArrayList<RewardItem>)AbstractDungeon.getCurrRoom().rewards.clone()) {
                switch (item.type) {
                    case CARD:
                        AbstractDungeon.getCurrRoom().addCardToRewards();
                        break;
                    case STOLEN_GOLD:
                    case GOLD:
                        AbstractDungeon.getCurrRoom().addGoldToRewards(item.goldAmt);
                        break;
                    case RELIC:
                        AbstractDungeon.getCurrRoom().addRelicToRewards(item.relic.tier);
                        break;
                    case POTION:
                        AbstractDungeon.getCurrRoom().addPotionToRewards(item.potion);
                        break;
                }
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlackStarRework();
    }
}
