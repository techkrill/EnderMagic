package ru.mousecray.endmagic.init;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import ru.mousecray.endmagic.api.blocks.EndSoilType;
import ru.mousecray.endmagic.items.BlueEnderPearl;
import ru.mousecray.endmagic.items.EMBook;
import ru.mousecray.endmagic.items.EMSeeds;
import ru.mousecray.endmagic.items.EnderApple;
import ru.mousecray.endmagic.items.EnderArrow;
import ru.mousecray.endmagic.items.EnderCompass;
import ru.mousecray.endmagic.items.ItemNamed;
import ru.mousecray.endmagic.items.ItemPortalBinder;
import ru.mousecray.endmagic.items.PurpleEnderPearl;
import ru.mousecray.endmagic.items.Test;
import ru.mousecray.endmagic.items.materials.EnderCoal;
import ru.mousecray.endmagic.items.materials.EnderDiamond;
import ru.mousecray.endmagic.items.materials.EnderSteel;
import ru.mousecray.endmagic.items.materials.MaterialProvider;
import ru.mousecray.endmagic.items.tools.EMArmor;
import ru.mousecray.endmagic.items.tools.EMAxe;
import ru.mousecray.endmagic.items.tools.EMHoe;
import ru.mousecray.endmagic.items.tools.EMPickaxe;
import ru.mousecray.endmagic.items.tools.EMShovel;
import ru.mousecray.endmagic.items.tools.EMSword;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.HSBA;
import ru.mousecray.endmagic.util.render.elix_x.ecomms.color.RGBA;

public class EMItems {
    public static final Item enderCompass = new EnderCompass();
    public static final ItemPortalBinder itemPortalBinder = new ItemPortalBinder();

    //public static final ItemTextured simpletexturemodel = ItemTextured.companion.simpletexturemodelItem; //may be unused

    private static final int naturalColor = new Color(0xEAB277).getRGB();
    private static final int phantomColor = new Color(0xA9D7F2).getRGB();
    private static final int dragonColor = new Color(0xA87DD2).getRGB();
    private static final int immortalColor = new Color(0xE5D67E).getRGB();

    public static final ItemNamed naturalCoal = new EnderCoal("natural_coal", naturalColor);
    public static final ItemNamed phantomCoal = new EnderCoal("phantom_coal", phantomColor);
    public static final ItemNamed dragonCoal = new EnderCoal("dragon_coal", dragonColor);
    public static final ItemNamed immortalCoal = new EnderCoal("immortal_coal", immortalColor);

    public static final ItemNamed naturalSteel = new EnderSteel("natural_steel", naturalColor, EMMaterials.NATURAL_STEEL_TOOL_MATERIAL, EMMaterials.NATURAL_STEEL_ARMOR_MATERIAL);
    public static final ItemNamed phantomSteel = new EnderSteel("phantom_steel", phantomColor, EMMaterials.PHANTOM_STEEL_TOOL_MATERIAL, EMMaterials.PHANTOM_STEEL_ARMOR_MATERIAL);
    public static final ItemNamed dragonSteel = new EnderSteel("dragon_steel", dragonColor, EMMaterials.DRAGON_STEEL_TOOL_MATERIAL, EMMaterials.DRAGON_STEEL_ARMOR_MATERIAL);
    public static final ItemNamed immortalSteel = new EnderSteel("immortal_steel", immortalColor, EMMaterials.IMMORTAL_STEEL_TOOL_MATERIAL, EMMaterials.IMMORTAL_STEEL_ARMOR_MATERIAL);

    public static final ItemNamed naturalDiamond = new EnderDiamond("natural_diamond", naturalColor, EMMaterials.NATURAL_DIAMOND_TOOL_MATERIAL);
    public static final ItemNamed phantomDiamond = new EnderDiamond("phantom_diamond", phantomColor, EMMaterials.PHANTOM_DIAMOND_TOOL_MATERIAL);
    public static final ItemNamed dragonDiamond = new EnderDiamond("dragon_diamond", dragonColor, EMMaterials.DRAGON_DIAMOND_TOOL_MATERIAL);
    public static final ItemNamed immortalDiamond = new EnderDiamond("immortal_diamond", immortalColor, EMMaterials.IMMORTAL_DIAMOND_TOOL_MATERIAL);

    private static final List<Item> steelToolsAndArmor;
    private static final List<Item> diamondTools;

    static {
        steelToolsAndArmor = Stream.of(naturalSteel, phantomSteel, dragonSteel, immortalSteel)
                .flatMap(material ->
                        {
                            int materialColor = material.textures().values().iterator().next();
                            HSBA hsba = RGBA.fromARGB(materialColor).toHSBA();
                            int toolColor = hsba.setB(0.7f).setA(0.5f).toRGBA().argb();
                            return Stream.of(
                                    new EMShovel(((MaterialProvider) material).material(), material.name() + "_shovel", toolColor),
                                    new EMPickaxe(((MaterialProvider) material).material(), material.name() + "_pickaxe", toolColor),
                                    new EMAxe(((MaterialProvider) material).material(), material.name() + "_axe", toolColor),
                                    new EMSword(((MaterialProvider) material).material(), material.name() + "_sword", toolColor),
                                    new EMHoe(((MaterialProvider) material).material(), material.name() + "_hoe", toolColor),
                                    new EMArmor(((MaterialProvider) material).armorMaterial(), 4, EntityEquipmentSlot.HEAD, material.name() + "_helmet", material, toolColor),
                                    new EMArmor(((MaterialProvider) material).armorMaterial(), 4, EntityEquipmentSlot.CHEST, material.name() + "_chestplate", material, toolColor),
                                    new EMArmor(((MaterialProvider) material).armorMaterial(), 4, EntityEquipmentSlot.LEGS, material.name() + "_leggings", material, toolColor),
                                    new EMArmor(((MaterialProvider) material).armorMaterial(), 4, EntityEquipmentSlot.FEET, material.name() + "_boots", material, toolColor)
                            );
                        }
                ).collect(Collectors.toList());

        diamondTools = Stream.of(naturalDiamond, phantomDiamond, dragonDiamond, immortalDiamond)
                .flatMap(material ->
                        {
                            int materialColor = material.textures().values().iterator().next();
                            HSBA hsba = RGBA.fromARGB(materialColor).toHSBA();
                            int toolColor = hsba.setS(hsba.getS() + 0.3f).setB(1).setA(0.5f).toRGBA().argb();
                            return Stream.of(
                                    new EMShovel(((MaterialProvider) material).material(), material.name() + "_shovel", toolColor),
                                    new EMPickaxe(((MaterialProvider) material).material(), material.name() + "_pickaxe", toolColor),
                                    new EMAxe(((MaterialProvider) material).material(), material.name() + "_axe", toolColor),
                                    new EMSword(((MaterialProvider) material).material(), material.name() + "_sword", toolColor),
                                    new EMHoe(((MaterialProvider) material).material(), material.name() + "_hoe", toolColor)
                            );
                        }
                ).collect(Collectors.toList());
    }

    public static List<Item> steelToolsAndArmor() {
        return steelToolsAndArmor;
    }

    public static List<Item> diamondTools() {
        return diamondTools;
    }

    public static final ItemNamed rawEnderite = new ItemNamed("raw_enderite");
    
    public static final Item purpleEnderPearl = new PurpleEnderPearl();
    public static final Item blueEnderPearl = new BlueEnderPearl();
    public static final Item enderArrow = new EnderArrow();
    public static final Item enderApple = new EnderApple();
    public static final Item emBook = new EMBook();
    public static final Item test = new Test();
    public static final Item enderSeeds = new EMSeeds(()->EMBlocks.enderCrops, "ender_seeds", "tooltip.ender_seeds", true, EndSoilType.GRASS);
}