package rorgmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class RadarScan extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Radar Scan";

    public RadarScan() {
        super(CARD_ID, DEFAULT_IMG_PATH_SKILL, 1, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setMagic(2, 1);
        setExhaust();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        applyAOEPower(new LockOnPower(null, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new RadarScan();
    }
}
