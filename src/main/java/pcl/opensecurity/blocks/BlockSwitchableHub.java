package pcl.opensecurity.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pcl.opensecurity.tileentity.TileEntitySwitchableHub;

public class BlockSwitchableHub extends BlockOSBase {

	public BlockSwitchableHub() {
		setUnlocalizedName("switchablehub");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySwitchableHub();
	}
}
