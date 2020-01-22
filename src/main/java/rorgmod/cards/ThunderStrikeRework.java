package rorgmod.cards;

import com.megacrit.cardcrawl.actions.defect.NewThunderStrikeAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;

import java.util.Iterator;

public class ThunderStrikeRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Thunder Strike";

    public ThunderStrikeRework() {
        super(CARD_ID, "rorgmod/cards/thunder_strike.png", 2, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ALL_ENEMY);
        setAttack(7, 2);
        this.tags.add(CardTags.STRIKE);
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 0;
        this.magicNumber = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Lightning) {
                ++this.baseMagicNumber;
            }
        }

        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }

        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseMagicNumber = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Lightning) {
                ++this.baseMagicNumber;
            }
        }

        this.magicNumber = this.baseMagicNumber;

        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new NewThunderStrikeAction(this));
        }

    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new ThunderStrikeRework();
    }
}
