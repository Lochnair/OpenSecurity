package pcl.opensecurity.blocks;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import pcl.opensecurity.tileentity.TileEntityRFIDReader;

/**
 * @author Caitlyn
 *
 */
public class BlockRFIDReader extends BlockOSBase {

	public BlockRFIDReader() {
		setUnlocalizedName("rfidreader");
		//setBlockTextureName("opensecurity:rfidreader");
	}
	
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void registerBlockIcons(IIconRegister icon) {
//		texture = icon.registerIcon("opensecurity:rfidreader");
//		texture_active = icon.registerIcon("opensecurity:rfidreader_active");
//		texture_found = icon.registerIcon("opensecurity:rfidreader_found");
//		texture_nofound = icon.registerIcon("opensecurity:rfidreader_nofound");
//	}
	
	//No rotation stuff...
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRFIDReader();
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.setBlockState(pos, state, 3);
	}
}
