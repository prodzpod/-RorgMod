package rorgmod.cards;

import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class Antivirus extends AbstractRorgCard {

    public static final String ID = "rorgmod:Antivirus";

    public Antivirus() {
        super(ID, "rorgmod/cards/anti_virus.png", 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(5, 3);
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
