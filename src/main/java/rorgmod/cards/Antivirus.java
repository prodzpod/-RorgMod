package rorgmod.cards;

import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class Antivirus extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Antivirus";

    public Antivirus() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 1, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        setBlock(7, 2);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int count = 0;
        for (AbstractMonster m2 : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m2.isDeadOrEscaped()) {
                count++;
            }
        }
        block(block, count);
        if (count >= 3) addToBot(new SFXAction("ATTACK_BOWLING"));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new Antivirus();
    }
}
