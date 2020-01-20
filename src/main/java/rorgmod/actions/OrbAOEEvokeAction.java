//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class OrbAOEEvokeAction extends AbstractGameAction {
    private AbstractOrb orb;
    private int times;

    public OrbAOEEvokeAction() { this(0, 1); }
    public OrbAOEEvokeAction(int times) { this(0, times); }
    public OrbAOEEvokeAction(AbstractOrb orb) { this(orb, 1); }
    public OrbAOEEvokeAction(int index, int times) { this(AbstractDungeon.player.orbs.get(index), times); }
    public OrbAOEEvokeAction(AbstractOrb orb, int times) {
        this.actionType = ActionType.DAMAGE;
        this.source = AbstractDungeon.player;
        this.orb = orb;
        this.times = times;
    }

    public void update() {
        switch (orb.ID) {
            case "Lightning":
            case "Dark":
                orb.triggerEvokeAnimation();
                this.addToBot(new DamageAllEnemiesAction(source,
                        DamageInfo.createDamageMatrix(orb.evokeAmount, true, true),
                        DamageType.THORNS, orb.ID.equals("Dark") ? AttackEffect.FIRE : AttackEffect.LIGHTNING, true));
                this.addToBot(new RemoveSpecificOrbAction(orb));
                break;
            case "Frost":
            case "Plasma":
            default:
                for (int i = 0; i < times; i++) this.addToBot(new EvokeSpecificOrbAction(orb));
        }
        this.isDone = true;
    }
}
