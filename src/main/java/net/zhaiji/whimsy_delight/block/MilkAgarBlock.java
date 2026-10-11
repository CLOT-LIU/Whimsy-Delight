package net.zhaiji.whimsy_delight.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

/**
 * 牛乳培养基：独立的完整方块，六个面都是奶斑贴图。
 * 牛乳菌作物只能种在它上面；挖掉后掉落自身，可重复放置利用。
 */
public class MilkAgarBlock extends Block {
    public MilkAgarBlock() {
        super(BlockBehaviour.Properties.of()
            .mapColor(MapColor.SNOW)
            .strength(0.6F)
            .sound(SoundType.GRAVEL)
        );
    }
}
