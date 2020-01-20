package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import rorgmod.RorgMod;

public class CriticalSection extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Critical Section";

    public CriticalSection() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 2, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setMagic(3, 2);
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
//        RorgMod.logger.info("PLAYED CRITICAL SECTION");
        RorgMod.logger.info(monster);
        applyAOEPower(new LockOnPower(null, magicNumber));
        applyAOEPower(new VulnerablePower(null, magicNumber, false));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new CriticalSection();
    }
}
