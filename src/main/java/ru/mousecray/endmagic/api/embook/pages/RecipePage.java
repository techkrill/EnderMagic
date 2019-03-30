package ru.mousecray.endmagic.api.embook.pages;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.mousecray.endmagic.api.embook.IPage;
import ru.mousecray.endmagic.api.embook.IStructuralGuiElement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RecipePage implements IPage {
    public final ItemStack result;

    public RecipePage(ItemStack result, ImmutableList<ImmutableList<Ingredient>> cratingGrid) {
        this.result = result;
        this.cratingGrid = cratingGrid;
    }

    public final ImmutableList<ImmutableList<Ingredient>> cratingGrid;

    /**
     * Constructor where find and prepare recipe grid for ItemStack
     *
     * @param result
     */
    public RecipePage(ItemStack result) {
        this.result = result;
        Optional<IRecipe> recipe = GameRegistry.findRegistry(IRecipe.class)
                .getValuesCollection()
                .stream()
                .filter(i -> ItemStack.areItemsEqual(i.getRecipeOutput(), result))
                .findFirst();
        Ingredient[][] cratingGrid1 = new Ingredient[3][3];

        Ingredient emptyStack = Ingredient.fromStacks(ItemStack.EMPTY);

        NonNullList<Ingredient> ingredients =
                recipe
                        .map(IRecipe::getIngredients)
                        .orElseGet(() -> NonNullList.from(emptyStack));

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int index = x + y * 3;
                if (ingredients.size() > index)
                    cratingGrid1[x][y] = ingredients.get(index);
                else
                    cratingGrid1[x][y] = emptyStack;
            }
        }

        cratingGrid = Stream.of(cratingGrid1).map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<IStructuralGuiElement> elements() {
        return null;
    }
}
