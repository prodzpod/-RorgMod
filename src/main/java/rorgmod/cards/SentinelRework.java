package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Sentinel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rorgmod.helpers.MetricHelper;

public class SentinelRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Sentinel";

    public SentinelRework() {
        super(ID, "rorgmod/cards/sentinel.png", 2, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(7, 3);
        REWORK_ID = Sentinel.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        block(block);
        if (MetricHelper.totalExhaustedThisTurn > 0) this.addToBot(new GainEnergyAction(2));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = MetricHelper.totalExhaustedThisTurn > 0 ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new SentinelRework();
    }
}
