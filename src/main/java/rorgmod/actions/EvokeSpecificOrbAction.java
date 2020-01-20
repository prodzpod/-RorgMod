package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class EvokeSpecificOrbAction extends AbstractGameAction {
    private AbstractOrb orb;
    private int times;

    public EvokeSpecificOrbAction(AbstractOrb orb) { this(0, 1); }
    public EvokeSpecificOrbAction(int index) { this(index, 1); }
    public EvokeSpecificOrbAction(int index, int times) { this(AbstractDungeon.player.orbs.get(index), times); }
    public EvokeSpecificOrbAction(AbstractOrb orb, int times) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orb = orb;
        this.times = times;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (this.orb != null) {
            AbstractDungeon.player.orbs.remove(this.orb);
            for (int i = 0; i < times; i++) {
                AbstractDungeon.player.orbs.add(0, this.orb);
                AbstractDungeon.player.evokeOrb();
            }
        }

        this.tickDuration();
    }
}

