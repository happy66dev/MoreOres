package io.github.happy66dev.moreores;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HeatedReactionChamber extends AContainer {

    private static final int[] BORDER_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private static final int[] INPUT_SLOTS = {10, 11, 12};
    private static final int[] OUTPUT_SLOTS = {14, 15, 16};
    private static final int PROGRESS_SLOT = 13;

    public HeatedReactionChamber(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public String getMachineIdentifier() {
        return "HEATED_REACTION_CHAMBER";
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.CLOCK);
    }

    @Override
    protected void constructMenu(BlockMenuPreset preset) {
        for (int slot : BORDER_SLOTS) {
            preset.addItem(slot,
                    new CustomItemStack(Material.GRAY_STAINED_GLASS_PANE, " "),
                    (p, s, item, action) -> false);
        }

        preset.addItem(PROGRESS_SLOT,
                new CustomItemStack(Material.CLOCK, "&7进行中..."),
                (p, s, item, action) -> false);

        for (int slot : OUTPUT_SLOTS) {
            preset.addMenuClickHandler(slot, (p, s, item, action) -> {
                if (item == null || item.getType().isAir()) return false;
                return !action.isShiftClicked();
            });
        }
    }

    @Override
    public String getInventoryTitle() {
        return "普通加热反应室";
    }

    public void registerRecipes() {
        registerRecipe(new MachineRecipe(3,
                new ItemStack[]{MoreOresItems.PYRITE_DUST},
                new ItemStack[]{new ItemStack(Material.RAW_IRON)}));

        registerRecipe(new MachineRecipe(6,
                new ItemStack[]{new ItemStack(Material.RAW_IRON), new ItemStack(Material.COAL)},
                new ItemStack[]{new ItemStack(Material.IRON_INGOT)}));

        registerRecipe(new MachineRecipe(6,
                new ItemStack[]{new ItemStack(Material.RAW_IRON), new ItemStack(Material.CHARCOAL)},
                new ItemStack[]{new ItemStack(Material.IRON_INGOT)}));

        ItemStack yellowIronOre2 = MoreOresItems.YELLOW_IRON_ORE.clone();
        yellowIronOre2.setAmount(2);
        registerRecipe(new MachineRecipe(12,
                new ItemStack[]{yellowIronOre2},
                new ItemStack[]{new ItemStack(Material.RAW_IRON, 3)}));
    }
}
