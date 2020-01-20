package rorgmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Reboot extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Reboot";

    public Reboot() {
        super(CARD_ID, "rorgmod/cards/reboot.png", 0, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(4, 2);
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new ShuffleAllAction());
        this.addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
        this.addToBot(new DrawCardAction(player, this.magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Reboot();
    }
}
