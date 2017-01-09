package pcl.opensecurity.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import pcl.opensecurity.OpenSecurity;
import pcl.opensecurity.tileentity.TileEntityKVM;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockKVM extends BlockOSBase {

	@SideOnly(Side.CLIENT)
	private IIcon inputNoCon;
	@SideOnly(Side.CLIENT)
	private IIcon inputCon;
	@SideOnly(Side.CLIENT)
	private IIcon outputNoCon;
	@SideOnly(Side.CLIENT)
	private IIcon outputCon;

	public BlockKVM() {
		setBlockName("kvm");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityKVM();
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		super.onBlockPlacedBy(par1World, x, y, z, par5EntityLivingBase, par6ItemStack);
		int whichDirectionFacing = 0;
		whichDirectionFacing = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
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
		par1World.setBlockMetadataWithNotify(x, y, z, par5EntityLivingBase.isSneaking() ? whichDirectionFacing : ForgeDirection.OPPOSITES[whichDirectionFacing], 2);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, int metadata, float clickX, float clickY, float clickZ) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		player.openGui(OpenSecurity.instance, 1, world, x, y, z);
		return true;
	}


	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		inputNoCon  = icon.registerIcon("opensecurity:hub_output_noconnected");
		inputCon    = icon.registerIcon("opensecurity:hub_output_connected");
		outputNoCon = icon.registerIcon("opensecurity:hub_input_noconnected");
		outputCon   = icon.registerIcon("opensecurity:hub_input_connected");
	}


	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int metadata, int side) {
		if (side == 4 && metadata == 0) {
			return this.inputCon;
		}
		return this.outputNoCon;
	}


	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
		int metadata = block.getBlockMetadata(x, y, z);
		TileEntityKVM KVMTE = (TileEntityKVM) block.getTileEntity(x, y, z);

		if (metadata == 0 && side == 0)
			return this.inputCon;
		else if (metadata == 1 && side == 1)
			return this.inputCon;
		else if (metadata == 2 && side == 2)
			return this.inputCon;
		else if (metadata == 3 && side == 3)
			return this.inputCon;
		else if (metadata == 4 && side == 4)
			return this.inputCon;
		else if (metadata == 5 && side == 5)
			return this.inputCon;



		if (KVMTE.down && side == 0)
			return this.outputCon;
		else if (KVMTE.up && side == 1)
			return this.outputCon;
		else if (KVMTE.north && side == 2)
			return this.outputCon;
		else if (KVMTE.south && side == 3)
			return this.outputCon;
		else if (KVMTE.west && side == 4)
			return this.outputCon;
		else if (KVMTE.east && side == 5)
			return this.outputCon;


		return this.outputNoCon;
	}

}
