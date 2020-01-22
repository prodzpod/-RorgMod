package rorgmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.GashAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import rorgmod.actions.GashActionRework;
import rorgmod.actions.GashPlusAction;

public class ClawRework extends AbstractRorgCard {

    public static final String CARD_ID = "rorgmod:Claw";

    public ClawRework() {
        super(CARD_ID, "rorgmod/cards/claw.png", 0, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        setAttack(3, 0);
        setMagic(2, 0);
        setCustomUpgrade();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (monster != null) {
            addToBot(new VFXAction(new ClawEffect(monster.hb.cX, monster.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }
        dealDamage(monster, damage, AbstractGameAction.AttackEffect.NONE);
        if (upgraded) addToBot(new GashPlusAction(this, magicNumber));
        else addToBot(new GashActionRework(this, magicNumber));
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new ClawRework();
    }
}
