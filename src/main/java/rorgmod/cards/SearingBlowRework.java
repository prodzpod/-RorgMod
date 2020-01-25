package rorgmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;

public class SearingBlowRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Searing Blow";

    public SearingBlowRework() { this(0); }

    public SearingBlowRework(int upgrades) {
        super(CARD_ID, "rorgmod/cards/searing_blow.png", 2, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setAttack(16, 4);
        this.timesUpgraded = 0;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (monster != null) {
            this.addToBot(new VFXAction(new SearingBlowEffect(monster.hb.cX, monster.hb.cY, this.timesUpgraded), 0.2F));
        }
        dealDamage(monster, damage, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void upgrade() {
        this.upgradeDamage(this.ATTACK_UPGRADE + this.timesUpgraded);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new SearingBlowRework(this.timesUpgraded);
    }
}
