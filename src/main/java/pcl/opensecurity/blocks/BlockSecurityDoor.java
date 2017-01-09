package pcl.opensecurity.blocks;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pcl.opensecurity.ContentRegistry;
import pcl.opensecurity.OpenSecurity;
import pcl.opensecurity.tileentity.TileEntityDoorController;
import pcl.opensecurity.tileentity.TileEntitySecureDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSecurityDoor extends BlockDoor {
	public Item placerItem;

	@SideOnly(Side.CLIENT)
	private IIcon[] iconsUpper;
	@SideOnly(Side.CLIENT)
	private IIcon[] iconsLower;

	public BlockSecurityDoor()
	{
		super(Material.IRON);
		float f = 0.5F;
		float f1 = 1.0F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		this.setHardness(400F);
		this.setResistance(6000F);
		this.setBlockName("securityDoor");
		this.setBlockTextureName(OpenSecurity.MODID + ":door_secure_upper");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int par5)
	{
		if (par5 != 1 && par5 != 0)
		{
			int blockMeta = this.func_150012_g(blockAccess, x, y, z);
			int j1 = blockMeta & 3;
			boolean isLowerPanel = (blockMeta & 4) != 0;
			boolean flipped = false;
			boolean isUpperPanel = (blockMeta & 8) != 0;

			if (isLowerPanel)
			{
				if (j1 == 0 && par5 == 2) flipped = !flipped;
				else if (j1 == 1 && par5 == 5) flipped = !flipped;
				else if (j1 == 2 && par5 == 3) flipped = !flipped;
				else if (j1 == 3 && par5 == 4) flipped = !flipped;
			}
			else
			{
				if (j1 == 0 && par5 == 5) flipped = !flipped;
				else if (j1 == 1 && par5 == 3) flipped = !flipped;
				else if (j1 == 2 && par5 == 4) flipped = !flipped;
				else if (j1 == 3 && par5 == 2) flipped = !flipped;

				if ((blockMeta & 16) != 0)
				{
					flipped = !flipped;
				}
			}

			return isUpperPanel ? this.iconsUpper[flipped?1:0] : this.iconsLower[flipped?1:0];
		}
		else
		{
			return this.iconsLower[0];
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		return this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.iconsUpper = new IIcon[2];
		this.iconsLower = new IIcon[2];
		this.iconsUpper[0] = p_149651_1_.registerIcon(OpenSecurity.MODID + ":door_secure_upper");
		this.iconsLower[0] = p_149651_1_.registerIcon(OpenSecurity.MODID + ":door_secure_lower");
		this.iconsUpper[1] = new IconFlipped(this.iconsUpper[0], true, false);
		this.iconsLower[1] = new IconFlipped(this.iconsLower[0], true, false);
	}

	@Override
	public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		return false;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return ContentRegistry.securityDoorItem;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(ContentRegistry.securityDoorItem);

	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		TileEntitySecureDoor tileEntity = (TileEntitySecureDoor) world.getTileEntity(x, y, z);
		//If the user is not the owner, or the user is not in creative drop out.
		if((tileEntity.getOwner()!=null && tileEntity.getOwner().equals(player.getUniqueID().toString())) || player.capabilities.isCreativeMode){
			this.setResistance(4F);
			this.setHardness(6F);
		} else {
			this.setResistance(400F);
			this.setHardness(6000F);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int meta = world.getBlockMetadata(x, y, z);

		if ((meta & 8) == 0) //Top door block
		{

			if (world.getBlock(x, y + 1, z) != this)
			{
				world.setBlockToAir(x, y, z);
			}

			if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z))
			{
				world.setBlockToAir(x, y, z);

				if (world.getBlock(x, y + 1, z) == this)
				{
					world.setBlockToAir(x, y + 1, z);
				}
			}
		}
		else //Bottom door block
		{
			if (world.getBlock(x, y - 1, z) != this)
			{
				world.setBlockToAir(x, y, z);
			}

			if (block != this)
			{
				this.onNeighborBlockChange(world, x, y - 1, z, block);
			}
		}
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySecureDoor();
	}
	
	@Override
	public int getMobilityFlag() {
		return 2;
	}
	
	
}