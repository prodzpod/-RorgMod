package rorgmod.cards;

import com.megacrit.cardcrawl.cards.green.ToolsOfTheTrade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ToolsOfTheTradePower;

public class ToolsOfTheTradeRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Tools of the Trade";

    public ToolsOfTheTradeRework() {
        super(ID, "rorgmod/cards/tools_of_the_trade.png", 1, CardType.POWER, CardColor.GREEN, CardRarity.RARE, CardTarget.SELF);
        upgradeToInnate();
        setCustomUpgrade();
        REWORK_ID = ToolsOfTheTrade.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applySelfPower(new ToolsOfTheTradePower(player, 1));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new ToolsOfTheTradeRework();
    }
}
