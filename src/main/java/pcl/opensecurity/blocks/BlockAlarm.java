package pcl.opensecurity.blocks;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import pcl.opensecurity.tileentity.TileEntityAlarm;

/**
 * @author Caitlyn
 *
 */
public class BlockAlarm extends BlockOSBase {

	public BlockAlarm() {
		setUnlocalizedName("alarm");
		//setBlockTextureName("opensecurity:alarm");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityAlarm();
	}

	@Override
	public boolean onBlockEventReceived(World world, BlockPos pos, int eventId, int eventPramater) {
		TileEntityAlarm tile = (TileEntityAlarm) world.getTileEntity(pos);
		if (eventId == 0 && !tile.computerPlaying) {
			tile.setShouldStart(true);
		}
		if (world.isRemote && eventId == 1 && !tile.computerPlaying) {
			tile.setShouldStop(true);
		}
		return true;
	}
}
