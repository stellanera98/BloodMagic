package wayoftime.bloodmagic.common.block;

import net.minecraft.world.level.block.SoundType;
import wayoftime.bloodmagic.common.block.base.BaseTileBlock;
import wayoftime.bloodmagic.common.blockentity.BMTiles;
import wayoftime.bloodmagic.common.blockentity.BellJarTile;

public class BellJarBlock extends BaseTileBlock<BellJarTile> {

    public BellJarBlock() {
        super(Properties.of()
                        .noOcclusion()
                        .sound(SoundType.GLASS)
                        .strength(2, 5),
                BMTiles.BELLJAR_TYPE
        );
    }
}
