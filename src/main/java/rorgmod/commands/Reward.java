package rorgmod.commands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class Reward extends ConsoleCommand {
    public Reward() {
        maxExtraTokens = 2; // args1 = relic / card / potion
        minExtraTokens = 1; // args2 = pool name (common, uncommon, rare, boss, shop) / (normal, elite, boss) / (common, uncommon, rare) // arg3 = amount in int
        requiresPlayer = false; //if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        String args1[] = { "relic", "card", "potion" };
        String args2a[] = { "common", "uncommon", "rare", "boss", "shop" };
        String args2c[] = { "common", "uncommon", "rare" };
        String args3[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        String args3c[] = { "1", "2", "3", "4", "5" };

        switch (depth) {
            case 0:
                for (String s : args1) result.add(s);
                break;
            case 1:
                switch (tokens[1]) {
                    case "relic":
                        for (String s : args2a) result.add(s);
                        break;
                    case "card":
                        for (String s : args3) result.add(s);
                        break;
                    case "potion":
                        for (String s : args2c) result.add(s);
                        break;
                }
                break;
            case 2:
                switch (tokens[1]) {
                    case "relic":
                        for (String s : args3) result.add(s);
                        break;
                    case "card":
                        complete = true;
                        break;
                    case "potion":
                        for (String s : args3c) result.add(s);
                        break;
                }
                break;
            case 3:
                complete = true;
                break;
        }
        return result;
    }

    @Override
    public void errorMsg() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* relic <pool> [amt]");
        DevConsole.log("* card [amt]");
        DevConsole.log("* potion <pool> [amt]");
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        String pool = tokens[2];
        int amount = depth == 3 ? Integer.parseInt(tokens[3]) : 1;
        AbstractDungeon.getCurrRoom().rewards.clear();
        switch (tokens[1]) {
            case "relic":
                AbstractRelic.RelicTier tier = AbstractRelic.RelicTier.DEPRECATED;
                switch (pool) {
                    case "common":
                        tier = AbstractRelic.RelicTier.COMMON;
                        break;
                    case "uncommon":
                        tier = AbstractRelic.RelicTier.UNCOMMON;
                        break;
                    case "rare":
                        tier = AbstractRelic.RelicTier.RARE;
                        break;
                    case "boss":
                        tier = AbstractRelic.RelicTier.BOSS;
                        break;
                    case "shop":
                        tier = AbstractRelic.RelicTier.SHOP;
                        break;
                }
                for (int i = 0; i < amount; i++) AbstractDungeon.getCurrRoom().addRelicToRewards(tier);
                break;
            case "card":
                for (int i = 0; i < Integer.parseInt(pool); i++) AbstractDungeon.getCurrRoom().addCardToRewards();
                break;
            case "potion":
                AbstractPotion.PotionRarity tier2 = AbstractPotion.PotionRarity.COMMON;
                switch (pool) {
                    case "common":
                        tier2 = AbstractPotion.PotionRarity.COMMON;
                        break;
                    case "uncommon":
                        tier2 = AbstractPotion.PotionRarity.UNCOMMON;
                        break;
                    case "rare":
                        tier2 = AbstractPotion.PotionRarity.RARE;
                        break;
                }
                for (int i = 0; i < Integer.parseInt(pool); i++) AbstractDungeon.getCurrRoom().addPotionToRewards(AbstractDungeon.returnRandomPotion(tier2, false));
                break;
        }
        AbstractDungeon.combatRewardScreen.open();
    }
}
