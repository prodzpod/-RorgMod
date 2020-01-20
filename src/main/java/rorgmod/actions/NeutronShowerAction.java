package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.RemoveAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class NeutronShowerAction extends AbstractGameAction {

    public NeutronShowerAction(AbstractPlayer target) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.target = target;
    }

    public void update() {
        int orbCount = AbstractDungeon.player.filledOrbCount();
        this.addToBot(new RemoveAllOrbsAction());
        for (int i = 0; i < orbCount; i++) {
            AbstractOrb orb = AbstractOrb.getRandomOrb(true);
            addToBot(new ChannelAction(orb));
            addToBot(new TriggerPassiveAction(orb));
        }
        this.isDone = true;
    }
}
