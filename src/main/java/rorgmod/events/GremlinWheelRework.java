//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package rorgmod.events;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import rorgmod.monsters.WheelGremlin;

public class GremlinWheelRework extends GremlinWheelGame {
    private CUR_SCREEN screen;

    public GremlinWheelRework() {
        super();
        this.screen = CUR_SCREEN.INTRO;
        this.imageEventText.clear();
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[10]);
        this.imageEventText.setDialogOption(OPTIONS[8]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        super.buttonEffect(buttonPressed);
                        this.screen = CUR_SCREEN.COMPLETE;
                        return;
                    case 1:
                        this.logMetric("Fighting Wheel Gremlin");
                        this.imageEventText.clearRemainingOptions();
                        this.screen = CUR_SCREEN.FIGHT;
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(WheelGremlin.ID);
                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomRelicTier());
                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.actNum * 100);
                        this.enterCombatFromImage();
                        AbstractDungeon.lastCombatMetricKey = WheelGremlin.ID;
                        return;
                    default:
                        this.imageEventText.clearRemainingOptions();
                        this.screen = CUR_SCREEN.LEAVE;
                        this.openMap();
                        return;
                }
            case COMPLETE:
                super.buttonEffect(buttonPressed);
                break;
            case LEAVE:
            case FIGHT:
                this.openMap();
                break;
            default:
                this.logMetric("Fled From Gremlin");
                this.openMap();
                return;
        }

    }

    public void reopen() {
        if ((this.screen != GremlinWheelRework.CUR_SCREEN.LEAVE) && (this.screen != GremlinWheelRework.CUR_SCREEN.FIGHT)) {
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = (float)Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            this.enterImageFromCombat();
            this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
            this.imageEventText.updateDialogOption(0, OPTIONS[2]);
            this.imageEventText.setDialogOption(OPTIONS[3]);
        }
    }

    private static enum CUR_SCREEN {
        INTRO,
        LEAVE,
        SPIN,
        COMPLETE,
        FIGHT;

        private CUR_SCREEN() {
        }
    }

    public void logMetric(String actionTaken) {
        AbstractEvent.logMetric(ID, actionTaken);
    }
}
