package rorgmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class PropelledFlight extends AbstractRorgCard {

    public static final String ID = "rorgmod:Propelled Flight";

    public PropelledFlight() {
        super(ID, DEFAULT_IMG_PATH_SKILL, 2, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(6, 2);
        BETA = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        block(block, 2);
        AbstractPower power = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        if (power != null && power.amount >= 2)
            applySelfPower(new NextTurnBlockPower(player, block));
    }

    public void triggerOnGlowCheck() {
        AbstractPower power = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        this.glowColor = power != null && power.amount >= 2 ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new PropelledFlight();
    }
}
