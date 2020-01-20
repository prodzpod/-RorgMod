package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class ShortcircuitAction extends AbstractGameAction {
    private int amount;

    public ShortcircuitAction(int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DAMAGE;
        this.amount = amount;
    }

    public void update() {
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                this.addToTop(new DamageAction(target, new DamageInfo(target, amount), AttackEffect.LIGHTNING, true));
            }
        }
        this.isDone = true;
    }
}
