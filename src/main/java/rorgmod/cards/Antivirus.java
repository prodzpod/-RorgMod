package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Antivirus extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Antivirus";

    public Antivirus() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 1, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        setBlock(7, 2);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        block(block, AbstractDungeon.getCurrRoom().monsters.monsters.size());
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Antivirus();
    }
}
