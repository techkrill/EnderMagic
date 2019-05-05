package ru.mousecray.endmagic.client.render.model.baked;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.mousecray.endmagic.util.RandomBlockPos;
import ru.mousecray.endmagic.util.elix_x.baked.UnpackedBakedQuad;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FinalisedModelEnderCompass extends BakedModelDelegate {
    private Vec3d current = new Vec3d(2, 0, 3);
    private final IBakedModel originalModel;
    private final ItemStack stack;
    private final EntityLivingBase entity;
    private final float nineteenDegs = (float) (Math.PI / 2);

    private static BlockPos getTarget() {
        return target.get(mc().world.provider.getDimension());
    }

    public static Map<Integer, BlockPos> target = new HashMap<Integer, BlockPos>() {
        private BlockPos randomPos = new RandomBlockPos();

        @Override
        public BlockPos get(Object key) {
            return super.getOrDefault(key, randomPos);
        }
    };

    public FinalisedModelEnderCompass(IBakedModel originalModel, ItemStack stack, EntityLivingBase entity) {
        super(originalModel);
        this.originalModel = originalModel;
        this.stack = stack;
        this.entity = entity;
    }

    private static List<BakedQuad> eye = new ArrayList<>();

    private static IBakedModel model = mc().getRenderItem()
            .getItemModelWithOverrides(new ItemStack(Items.ENDER_EYE), mc().world, null);

    private static List<BakedQuad> getEyeQuads() {
        if (eye.isEmpty()) {
            for (EnumFacing side1 : EnumFacing.values()) {
                eye.addAll(model.getQuads(null, side1, 0));
            }
            eye.addAll(model.getQuads(null, null, 0));
        }
        return eye;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return Lists.newArrayList(Iterables.concat(
                originalModel.getQuads(state, side, rand),
                generateEye(getEyePos())
        ));
    }

    private double calcAngle(Vec3d pos1, BlockPos pos) {
        return Math.atan2(pos1.x - pos.getX(), pos1.z - pos.getZ());
    }

    private Vec3d getEyePos() {
        double angle = calcAngle(entity.getPositionEyes(mc().getRenderPartialTicks()), getTarget()) + Math.toRadians(entity.rotationYaw + 90);
        current = current.add(new Vec3d(2 - Math.cos(angle), 0, 3 + Math.sin(angle)).subtract(current).scale(0.001));
        return current;
    }

    private static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    private List<BakedQuad> generateEye(Vec3d eyePos) {
        return getEyeQuads()
                .stream()
                .map(UnpackedBakedQuad::unpack)
                .peek(i -> i.getVertices().getVertices()
                        .forEach(v ->
                                v.setPos(v.getPos().rotatePitch(nineteenDegs).add(eyePos).scale(0.2))
                        ))
                .map(UnpackedBakedQuad::pack)
                .collect(Collectors.toList());
    }
}
