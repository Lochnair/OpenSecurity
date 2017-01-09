package pcl.opensecurity.blocks;

import li.cil.oc.common.item.Wrench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pcl.opensecurity.ContentRegistry;
import pcl.opensecurity.OpenSecurity;
import pcl.opensecurity.tileentity.TileEntityDoorController;

public class BlockDoorController extends BlockOSBase {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockDoorController() {
		super();
		this.setHardness(400F);
		this.setResistance(6000F);
		this.setUnlocalizedName("doorController");
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		TileEntity te = par1World.getTileEntity(x, y, z);
		((TileEntityDoorController) te).setOwner(par5EntityLivingBase.getUniqueID().toString());
		((TileEntityDoorController) te).overrideTexture(ContentRegistry.DoorControllerBlock, new ItemStack(Item.getItemFromBlock(ContentRegistry.DoorControllerBlock)), ForgeDirection.getOrientation(1));
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		TileEntityDoorController tileEntity = (TileEntityDoorController) world.getTileEntity(x, y, z);
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		TileEntityDoorController tileEntity = (TileEntityDoorController) world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}

		//If the user is not the owner, or the user is not in creative drop out.
		if(tileEntity.getOwner()!=null){
			if(!tileEntity.getOwner().equals(player.getUniqueID().toString()) && !player.capabilities.isCreativeMode) {
				if(!tileEntity.getOwner().isEmpty()) {
					return false;
				}
			}
		}
		//Change the block texture
		if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
			Block block = Block.getBlockFromItem(player.getCurrentEquippedItem().getItem());
			Item oldBlock = tileEntity.DoorControllerCamo[0].getItem();
			if (block.isOpaqueCube() || block instanceof BlockGlass || block instanceof BlockStainedGlass) {
				if (!tileEntity.DoorControllerCamo[0].getItem().equals(player.getCurrentEquippedItem().getItem())) {
					if(player.capabilities.isCreativeMode) {
						tileEntity.overrideTexture(block, player.getCurrentEquippedItem().splitStack(0), ForgeDirection.getOrientation(side));
					} else {
						tileEntity.overrideTexture(block, player.getCurrentEquippedItem().splitStack(1), ForgeDirection.getOrientation(side));
					}
					world.scheduleBlockUpdate(x, y, z, tileEntity.block, 5);
					if (!world.isRemote) {
						ItemStack testAgainst = new ItemStack(oldBlock);
						if (!testAgainst.getItem().equals(Item.getItemFromBlock(ContentRegistry.DoorControllerBlock))) {
							EntityItem myItemEntity = new EntityItem(world, x, y, z, testAgainst);
							world.spawnEntityInWorld(myItemEntity);
						}
					}
				}
			}
			//Remove the block texture with the scrench
		} else if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof Wrench) {
			if (!world.isRemote) {
				if (!tileEntity.DoorControllerCamo[0].getItem().equals(Item.getItemFromBlock(ContentRegistry.DoorControllerBlock))) {
					EntityItem myItemEntity = new EntityItem(world, x, y, z, tileEntity.DoorControllerCamo[0]);
					world.spawnEntityInWorld(myItemEntity);
				}
			}
			tileEntity.overrideTexture(ContentRegistry.DoorControllerBlock, new ItemStack(Item.getItemFromBlock(ContentRegistry.DoorControllerBlock)), ForgeDirection.getOrientation(side));
			world.scheduleBlockUpdate(x, y, z, tileEntity.block, 5);
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDoorController();
	}

	@SideOnly(Side.CLIENT)
	public static IIcon topIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon bottomIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon leftIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon rightIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon frontIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon backIcon;

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		topIcon = icon.registerIcon("opensecurity:machine_side");
		bottomIcon = icon.registerIcon("opensecurity:machine_side");
		leftIcon = rightIcon = frontIcon = backIcon = icon.registerIcon("opensecurity:door_controller");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 0) {
			return bottomIcon;
		} else if (side == 1) {
			return topIcon;
		} else if (side == 2) {
			return backIcon;
		} else if (side == 3) {
			return frontIcon;
		} else if (side == 4) {
			return rightIcon;
		} else if (side == 5) {
			return leftIcon;
		}
		return leftIcon;
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
		//int metadata = block.getBlockMetadata(x, y, z);
		TileEntityDoorController te = (TileEntityDoorController) block.getTileEntity(x, y, z);
		IIcon[] thisBlockTextures = new IIcon[6];

		if (te.DoorControllerCamo[0] != null) {
			te.overrideTexture(te.DoorControllerCamo[0]);
		}
		for (int getSide = 0; getSide < thisBlockTextures.length; getSide++) {
			if(te.blockTextures[getSide] != null) {
				if(side == getSide) {
					return te.blockTextures[getSide];
				}
			}
		}			


		return topIcon;
	}

}
