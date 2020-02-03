package rorgmod.monsters;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.ShelledParasite;

public class ShelledParasiteRework extends ShelledParasite {

    public ShelledParasiteRework() { super(); }
    public ShelledParasiteRework(float x, float y) { super(x, y); }

    @Override
    protected void getMove(int rng) {
        if (GameActionManager.turn % 4 == (AbstractDungeon.ascensionLevel >= 17 ? 0 : 2))
                                                            setMove((byte)1, AbstractMonster.Intent.ATTACK_DEBUFF, this.damage.get(1).base);
        else {
                 if (lastTwoMoves((byte)2))                 setMove((byte)3, AbstractMonster.Intent.ATTACK_BUFF  , this.damage.get(2).base);
            else if (lastTwoMoves((byte)3))                 setMove((byte)2, AbstractMonster.Intent.ATTACK       , this.damage.get(0).base, 2, true);
            else if (AbstractDungeon.aiRng.randomBoolean()) setMove((byte)3, AbstractMonster.Intent.ATTACK_BUFF  , this.damage.get(2).base);
            else                                            setMove((byte)2, AbstractMonster.Intent.ATTACK       , this.damage.get(0).base, 2, true);
        }
    }
}
