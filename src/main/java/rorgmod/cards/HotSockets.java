package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import rorgmod.actions.ShortcircuitAction;

public class HotSockets extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Hot Sockets";

    public HotSockets() {
        super(CARD_ID, DEFAULT_IMG_PATH_ATTACK, 1, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        setAttack(4,1);
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                count++;
            }
        }

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) { // singular
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        } else { // plural
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        evokeOrb(1, 1);
        this.addToBot(new ShortcircuitAction(this.damage));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new HotSockets();
    }
}
