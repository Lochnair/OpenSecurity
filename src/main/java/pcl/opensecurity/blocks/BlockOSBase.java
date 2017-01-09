package pcl.opensecurity.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockOSBase extends BlockContainer {

	protected BlockOSBase() {
		super(Material.IRON);
		this.setHardness(5F);
		this.setResistance(30F);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		int whichDirectionFacing = 0;
		if (placer.rotationPitch >= 70) {
			whichDirectionFacing = 0;// down
		} else if (placer.rotationPitch <= -70) {
			whichDirectionFacing = 1;// up
		} else {
			whichDirectionFacing = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			switch (whichDirectionFacing) {
			case 0:
				whichDirectionFacing = ForgeDirection.SOUTH.ordinal();
				break;
			case 1:
				whichDirectionFacing = ForgeDirection.WEST.ordinal();
				break;
			case 2:
				whichDirectionFacing = ForgeDirection.NORTH.ordinal();
				break;
			case 3:
				whichDirectionFacing = ForgeDirection.EAST.ordinal();
				break;
			default:
				break;
			}
		}

		worldIn.setBlockMetadataWithNotify(x, y, z, par5EntityLivingBase.isSneaking() ? whichDirectionFacing : ForgeDirection.OPPOSITES[whichDirectionFacing], 2);
	}

}
