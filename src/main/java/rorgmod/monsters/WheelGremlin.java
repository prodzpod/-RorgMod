package rorgmod.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

public class WheelGremlin extends CustomMonster {
    public static final String ID = "rorgmod:Wheel Gremlin";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final int HP_MIN = 21;
    private static final int HP_MAX = 25;
    private static final int A_2_HP_MIN = 44;
    private static final int A_2_HP_MAX = 52;
    private static final int A_3_HP_MIN = 88;
    private static final int A_3_HP_MAX = 104;
    private static final int MAGIC_DAMAGE = 8;
    private static final int A_2_MAGIC_DAMAGE = 11;
    private static int ASCENSION_INCREMENT = 0;

    public WheelGremlin() {
        super(NAME, ID, HP_MAX, 40.0F, -5.0F, 130.0F, 180.0F, (String)null);
        this.dialogX = 0.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 7) ASCENSION_INCREMENT = 3;
        switch (AbstractDungeon.actNum) {
            case 1:
            default:
                this.setHp(HP_MIN + ASCENSION_INCREMENT, HP_MAX + ASCENSION_INCREMENT);
                break;
            case 2:
                this.setHp(A_2_HP_MIN + ASCENSION_INCREMENT, A_2_HP_MAX + ASCENSION_INCREMENT);
                break;
            case 3:
                this.setHp(A_3_HP_MIN + ASCENSION_INCREMENT, A_3_HP_MAX + ASCENSION_INCREMENT);
                break;
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.damage.add(new DamageInfo(this, A_2_MAGIC_DAMAGE));
        } else {
            this.damage.add(new DamageInfo(this, MAGIC_DAMAGE));
        }

        //this.loadAnimation("images/monsters/theBottom/wizardGremlin/skeleton.atlas", "images/monsters/theBottom/wizardGremlin/skeleton.json", 1.0F);
        //AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        //e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                for (int i = 0; i < AbstractDungeon.actNum; i++) AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new FastShakeAction(this, 0.5F, 0.2F));
                AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(new Decay()));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playDeathSfx() {
        switch (MathUtils.random(2)) {
            case 0: CardCrawlGame.sound.play("VO_GREMLINDOPEY_2A"); break;
            case 1: CardCrawlGame.sound.play("VO_GREMLINDOPEY_2B"); break;
            case 2: CardCrawlGame.sound.play("VO_GREMLINDOPEY_2C"); break;
        }

    }

    public void die() {
        super.die();
        this.playDeathSfx();
    }

    protected void getMove(int num) {
        if (this.lastTwoMoves((byte)1)) {
            this.setMove(MOVES[1], (byte)2, Intent.DEBUFF);
        } else {
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK, (this.damage.get(0)).base, AbstractDungeon.actNum, AbstractDungeon.actNum != 1);
        }
    }
}
