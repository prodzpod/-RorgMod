package rorgmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WraithFormAltPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Wraith Form 2";

    public WraithFormAltPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "rorgmod/powers/wraithForm", PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void onDiscardCard() {
        this.flash();
        this.addToBot(new GainBlockAction(owner, amount));
        AbstractCreature monster = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        this.addToBot(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}