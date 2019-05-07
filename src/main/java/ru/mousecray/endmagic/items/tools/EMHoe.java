package ru.mousecray.endmagic.items.tools;

import net.minecraft.item.ItemHoe;
import ru.mousecray.endmagic.util.NameProvider;

public class EMHoe extends ItemHoe implements NameProvider, ItemTexturedTool {

    private final String name;
    private final int color;

    public EMHoe(ToolMaterial material, String name, int color) {
        super(material);
        this.name = name;
        this.color = color;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toolType() {
        return "colorless_shovel";
    }

    @Override
    public int color() {
        return color;
    }
}
