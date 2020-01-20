package rorgmod.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AncientWritings extends AbstractImageEvent {
    public static final String ID = "rorgmod:Ancient Writings";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private AncientWritings.CUR_SCREEN screen;
    private List<String> cardsUpgraded;

    public AncientWritings() {
        super(NAME, DIALOG_1, "images/events/AncientWritings.jpg");
        this.screen = AncientWritings.CUR_SCREEN.INTRO;
        this.cardsUpgraded = new ArrayList();
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_ANCIENT");
        }

        this.cardsUpgraded.clear();
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.effectList.add(new PurgeCardEffect(c));
            AbstractEvent.logMetricCardRemoval(ID, "Elegance", c);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                if (buttonPressed == 0) {
                    if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                        this.imageEventText.updateBodyText(DIALOG_2);
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[2], false);
                    }

                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    this.imageEventText.clearRemainingOptions();
                } else {
                    this.imageEventText.updateBodyText(DIALOG_3);
                    this.upgradeStrikeAndDefends();
                    this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                    this.imageEventText.clearRemainingOptions();
                }

                this.screen = AncientWritings.CUR_SCREEN.COMPLETE;
                break;
            case COMPLETE:
                this.openMap();
        }

    }

    private void upgradeStrikeAndDefends() {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE), MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT));

        while(true) {
            AbstractCard c;
            do {
                if (!var1.hasNext()) {
                    AbstractEvent.logMetricUpgradeCards(ID, "Simplicity", this.cardsUpgraded);
                    return;
                }

                c = (AbstractCard)var1.next();
            } while(!c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) && !c.hasTag(AbstractCard.CardTags.STARTER_STRIKE));

            if (c.canUpgrade()) {
                c.upgrade();
                this.cardsUpgraded.add(c.cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT));
            }
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }

    private static enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        private CUR_SCREEN() {
        }
    }
}