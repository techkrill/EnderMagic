package ru.mousecray.endmagic.items;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import ru.mousecray.endmagic.EM;
import ru.mousecray.endmagic.client.render.model.IModelRegistration;
import ru.mousecray.endmagic.util.CreativeTabProvider;
import ru.mousecray.endmagic.util.IEMModel;

import java.util.Map;

public interface ItemTextured extends IEMModel {

    Map<String, Integer> textures();

    @Override
    default void registerModels(IModelRegistration modelRegistration) {
        textures().keySet().forEach(t -> modelRegistration.registerTexture(new ResourceLocation(t)));
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, companion.simpletexturemodel);
        ModelLoader.setCustomModelResourceLocation((Item) this, 0, companion.simpletexturemodel);
    }

    class companion {
        //may be unused
        public static ItemTextured simpletexturemodelItem = new ItemTextured() {
            public Map<String, Integer> textures() {
                return ImmutableMap.of("none", 0xffffff);
            }

            public CreativeTabs creativeTab() {
                return null;
            }
        };
        public static ModelResourceLocation simpletexturemodel = new ModelResourceLocation(EM.ID + ":simpletexturemodel", "inventory");

    }
}
