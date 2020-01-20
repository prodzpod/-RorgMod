package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CrookedMotherboard extends AbstractRorgRelic {
    public static String ID = "rorgmod:Crooked Motherboard";
    public CrookedMotherboard() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void onApplyPower(AbstractPower powerToApply, AbstractCreature target, AbstractCreature source) {
        if (target instanceof AbstractPlayer && source instanceof AbstractPlayer && powerToApply.type == AbstractPower.PowerType.DEBUFF) {
            this.flash();
            this.addToBot(new ApplyPowerAction(target, target, new ArtifactPower(target, 1)));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CrookedMotherboard();
    }
}
