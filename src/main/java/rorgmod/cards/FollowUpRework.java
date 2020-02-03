package rorgmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.FollowUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

import java.util.ArrayList;

public class FollowUpRework extends AbstractRorgCard {

    public static final String ID = "rorgmod:Follow-Up";

    public FollowUpRework() {
        super(ID, "rorgmod/cards/follow_up.png", 1, CardType.ATTACK, CardColor.PURPLE, CardRarity.COMMON, CardTarget.ENEMY);
        setAttack(7, 4);
        REWORK_ID = FollowUp.ID;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), Settings.FAST_MODE ? 0.0F : 0.3F));
        dealDamage(monster, damage, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        ArrayList<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        if (cards.size() >= 2 && (cards.get(cards.size() - 1)).type == CardType.ATTACK) {
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(player, 1));
        }
    }

    public void triggerOnGlowCheck() {
        ArrayList<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        this.glowColor = (!cards.isEmpty() && (cards.get(cards.size() - 1)).type == AbstractCard.CardType.ATTACK) ?
                AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public AbstractRorgCard makeCopy() {
        return new FollowUpRework();
    }
}
