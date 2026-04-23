package wayoftime.bloodmagic.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import wayoftime.bloodmagic.common.blockentity.BellJarTile;
import wayoftime.bloodmagic.common.blockentity.BloodTankTile;
import wayoftime.bloodmagic.common.datamap.WillStack;
import wayoftime.bloodmagic.util.RenderHelper;

public class WillJarRenderer implements BlockEntityRenderer<BellJarTile> {
    public WillJarRenderer(BlockEntityRendererProvider.Context context) {}

    // Tank inside
    private static final float minHeight = 4F/16F + 0.01F;
    private static final float maxHeight = 16F/16F - 0.01F;
    private static final float start = 0F/16F + 0.01F; // inside corner
    private static final float end = 16F/16F - 0.01F; // other inside corner

    @Override
    public void render(BellJarTile blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        /* TODO this
        Minecraft minecraft = Minecraft.getInstance();
        WillStack willStack = blockEntity.getStoredWill();
        if (willStack.amount() <= 0) {
            return;
        }
        TextureAtlasSprite sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidClientInfo.getStillTexture());
        int color = fluidClientInfo.getTintColor();

        poseStack.pushPose();

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.);
        float capacityHeight = maxHeight - minHeight;
        float height = (float) ((willStack.amount() / blockEntity.getCapacity()) * capacityHeight);

        RenderHelper.addCubeAll(vertexConsumer, poseStack, sprite, color, packedLight, packedOverlay, start, minHeight, start, end, height, end);

        poseStack.popPose();

         */
    }
}
