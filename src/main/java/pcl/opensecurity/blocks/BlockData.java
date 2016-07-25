package pcl.opensecurity.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pcl.opensecurity.tileentity.TileEntityDataBlock;

public class BlockData extends BlockOSBase {

	public BlockData() {
		setUnlocalizedName("datablock");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDataBlock();
	}

	//@SideOnly(Side.CLIENT)
	//@Override
	//public void registerBlockIcons(IIconRegister icon) {
	//	faceIcon = icon.registerIcon("opensecurity:dataprocblock_front");
	//	sideIcon = icon.registerIcon("opensecurity:dataprocblock_side");
	//}

}
