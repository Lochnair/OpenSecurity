package pcl.opensecurity.blocks;

//import li.cil.oc.common.item.Wrench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pcl.opensecurity.ContentRegistry;
import pcl.opensecurity.tileentity.TileEntityDoorController;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDoorController extends BlockOSBase {

	public BlockDoorController() {
		super();
		setUnlocalizedName("doorController");
		//setBlockTextureName("opensecurity:door_controller");
		this.setHardness(400F);
		this.setResistance(6000F);
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity te = worldIn.getTileEntity(pos);
		((TileEntityDoorController) te).setOwner(placer.getUniqueID().toString());
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		TileEntityDoorController tileEntity = (TileEntityDoorController) worldIn.getTileEntity(pos);
		//If the user is not the owner, or the user is not in creative drop out.
		if((tileEntity.getOwner()!=null && tileEntity.getOwner().equals(playerIn.getUniqueID().toString())) || playerIn.capabilities.isCreativeMode){
			this.setResistance(4F);
			this.setHardness(6F);
		} else {
			this.setResistance(400F);
			this.setHardness(6000F);
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityDoorController tileEntity = (TileEntityDoorController) worldIn.getTileEntity(pos);
		if (tileEntity == null || playerIn.isSneaking()) {
			return false;
		}
//Block camo is going to be hard to implement in 1.8.9 I'll come back to this.
/*		//If the user is not the owner, or the user is not in creative drop out.
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
					world.scheduleBlockUpdate(pos, tileEntity.block, 5);
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
			world.scheduleBlockUpdate(pos, tileEntity.block, 5);
		} */
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDoorController();
	}

	//@SideOnly(Side.CLIENT)
	//@Override
	//public void registerBlockIcons(IIconRegister icon) {
	//	topIcon = icon.registerIcon("opensecurity:machine_side");
	//	bottomIcon = icon.registerIcon("opensecurity:machine_side");
	//	leftIcon = rightIcon = frontIcon = backIcon = icon.registerIcon("opensecurity:door_controller");
	//}

	//@Override
	//public boolean isOpaqueCube(){
	//	return false;
	//}
}
