package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;

public class ZingZapAction extends AbstractGameAction {

    private AbstractPlayer source;
    private int amount;

    public ZingZapAction(int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.source = AbstractDungeon.player;
        this.amount = amount;
    }

    public void update() {
        for(int i = 0; i < source.orbs.size(); ++i) {
            if (source.orbs.get(i) instanceof Lightning) {
                this.addToTop(new GainBlockAction(source, amount));
            }
        }
        this.isDone = true;
    }
}
