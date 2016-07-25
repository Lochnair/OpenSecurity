package pcl.opensecurity.blocks;

import java.util.Random;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import pcl.opensecurity.items.ItemMagCard;
import pcl.opensecurity.tileentity.TileEntityMagReader;

/**
 * @author Caitlyn
 *
 */
public class BlockMagReader extends BlockOSBase {

	public BlockMagReader() {
		setUnlocalizedName("magreader");
		// setBlockTextureName("opensecurity:magreader");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		Item equipped = playerIn.getCurrentEquippedItem() != null ? playerIn.getCurrentEquippedItem().getItem() : null;
		TileEntityMagReader tile = (TileEntityMagReader) worldIn.getTileEntity(pos);
		if (!worldIn.isRemote && equipped instanceof ItemMagCard) {	
			if (tile.doRead(playerIn.getCurrentEquippedItem(), playerIn, side)) {
				worldIn.setBlockState(pos, state.withProperty(active, true));
			}
			worldIn.scheduleBlockUpdate(pos, this, 20, 0);
		}
		return true;
	}

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        worldIn.setBlockState(pos, state.withProperty(active, false));
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMagReader();
	}
}