package wayoftime.bloodmagic.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import wayoftime.bloodmagic.BloodMagic;
import wayoftime.bloodmagic.common.blockentity.BellJarTile;
import wayoftime.bloodmagic.common.blockentity.base.SingleTargetWillInteractor;
import wayoftime.bloodmagic.common.datamap.WillStack;
import wayoftime.bloodmagic.util.RenderHelper;

public class BellJarRenderer extends WillRenderer {

    // Tank inside
    private static final float minHeight = 4F/16F + 0.01F;
    private static final float maxHeight = 16F/16F - 0.01F;
    private static final float start = 0F/16F + 0.01F; // inside corner
    private static final float end = 16F/16F - 0.01F; // other inside corner

    private static final TextureAtlasSprite RAW = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(BloodMagic.rl("block/will_raw"));
    private static final TextureAtlasSprite CORROSIVE = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(BloodMagic.rl("block/will_corrosive"));
    private static final TextureAtlasSprite DESTRUCTIVE = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(BloodMagic.rl("block/will_destructive"));
    private static final TextureAtlasSprite STEADFAST = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(BloodMagic.rl("block/will_steadfast"));
    private static final TextureAtlasSprite VENGEFUL = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(BloodMagic.rl("block/will_vengeful"));

    public BellJarRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SingleTargetWillInteractor stwi, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.render(stwi, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        if (!(stwi instanceof BellJarTile blockEntity)) {
            return;
        }

        WillStack willStack = blockEntity.getStoredWill();
        if (willStack.amount() <= 0) {
            return;
        }
        TextureAtlasSprite sprite = switch (willStack.type()) {
            case CORROSIVE -> CORROSIVE;
            case DESTRUCTIVE -> DESTRUCTIVE;
            case STEADFAST -> STEADFAST;
            case VENGEFUL -> VENGEFUL;

            default -> RAW;
        };

        int color = 0xFF_FF_FF_FF;

        poseStack.pushPose();

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.SOLID);
        float capacityHeight = maxHeight - minHeight;
        float height = (float) ((willStack.amount() / blockEntity.getCapacity()) * capacityHeight);

        RenderHelper.addCubeAll(vertexConsumer, poseStack, sprite, color, packedLight, packedOverlay, start, minHeight, start, end, minHeight + height, end);

        poseStack.popPose();
    }
}
