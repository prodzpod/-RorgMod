package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class AddWithoutChannelingOrbAction extends AbstractGameAction {
    private AbstractOrb orbType;
    private boolean autoEvoke = false;
    public AddWithoutChannelingOrbAction(AbstractOrb newOrbType) {
        this(newOrbType, true);
    }
    public AddWithoutChannelingOrbAction(AbstractOrb newOrbType, boolean autoEvoke) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orbType = newOrbType;
        this.autoEvoke = autoEvoke;
    }
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.maxOrbs <= 0) {
                AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, AbstractPlayer.MSG[4], true));
                return;
            }
            int index = -1;
            for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
                if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                orbType.cX = AbstractDungeon.player.orbs.get(index).cX;
                orbType.cY = AbstractDungeon.player.orbs.get(index).cY;
                AbstractDungeon.player.orbs.set(index, orbType);
                AbstractDungeon.player.orbs.get(index).setSlot(index, AbstractDungeon.player.maxOrbs);
                orbType.playChannelSFX();
                int plasmaCount = 0;
                for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisTurn) {
                    if (o instanceof com.megacrit.cardcrawl.orbs.Plasma) {
                        plasmaCount++;
                    }
                }
                if (plasmaCount == 9) {
                    UnlockTracker.unlockAchievement("NEON");
                }
                orbType.applyFocus();
            } else {
                AbstractDungeon.actionManager.addToTop(new AddWithoutChannelingOrbAction(orbType));
                AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(1));
                AbstractDungeon.actionManager.addToTop(new AnimateOrbAction(1));
            }
        }
        tickDuration();
    }
}
