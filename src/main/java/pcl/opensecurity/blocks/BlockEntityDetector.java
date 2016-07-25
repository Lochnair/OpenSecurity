package pcl.opensecurity.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import pcl.opensecurity.tileentity.TileEntityEntityDetector;

public class BlockEntityDetector extends BlockContainer {

	public BlockEntityDetector() {
		super(Material.iron);
		setUnlocalizedName("entitydetector");
		//setBlockTextureName("opensecurity:entitydetector");
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityEntityDetector();
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.setBlockState(pos, state, 3);
	}
	
}