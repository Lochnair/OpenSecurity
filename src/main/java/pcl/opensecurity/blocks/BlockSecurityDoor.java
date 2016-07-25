package pcl.opensecurity.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import pcl.opensecurity.ContentRegistry;
import pcl.opensecurity.tileentity.TileEntitySecureDoor;

public class BlockSecurityDoor extends BlockDoor {
	public Item placerItem;

	public BlockSecurityDoor()
	{
		super(Material.iron);
		float f = 0.5F;
		float f1 = 1.0F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		this.setHardness(400F);
		this.setResistance(6000F);
		this.setUnlocalizedName("securityDoor");
		//this.setBlockTextureName(OpenSecurity.MODID + ":door_secure_upper");
	}

/*	@SideOnly(Side.CLIENT)
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
	}*/

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ContentRegistry.securityDoorItem;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(ContentRegistry.securityDoorItem);

	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		TileEntitySecureDoor tileEntity = (TileEntitySecureDoor) world.getTileEntity(pos);
		//If the user is not the owner, or the user is not in creative drop out.
		if((tileEntity.getOwner()!=null && tileEntity.getOwner().equals(player.getUniqueID().toString())) || player.capabilities.isCreativeMode){
			this.setResistance(4F);
			this.setHardness(6F);
		} else {
			this.setResistance(400F);
			this.setHardness(6000F);
		}
	}
	
    /**
     * Called when a neighboring block changes.
     */
	@Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER)
        {
            BlockPos blockpos = pos.down();
            IBlockState iblockstate = worldIn.getBlockState(blockpos);

            if (iblockstate.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
            }
            else if (neighborBlock != this)
            {
                this.onNeighborBlockChange(worldIn, blockpos, iblockstate, neighborBlock);
            }
        }
        else
        {
            boolean flag1 = false;
            BlockPos blockpos1 = pos.up();
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;
            }

            if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down()))
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;

                if (iblockstate1.getBlock() == this)
                {
                    worldIn.setBlockToAir(blockpos1);
                }
            }

            if (flag1)
            {
                if (!worldIn.isRemote)
                {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            }
            else
            {
                boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos1);

                if ((flag || neighborBlock.canProvidePower()) && neighborBlock != this && flag != ((Boolean)iblockstate1.getValue(POWERED)).booleanValue())
                {
                    worldIn.setBlockState(blockpos1, iblockstate1.withProperty(POWERED, Boolean.valueOf(flag)), 2);

                    if (flag != ((Boolean)state.getValue(OPEN)).booleanValue())
                    {
                        worldIn.setBlockState(pos, state.withProperty(OPEN, Boolean.valueOf(flag)), 2);
                        worldIn.markBlockRangeForRenderUpdate(pos, pos);
                        worldIn.playAuxSFXAtEntity((EntityPlayer)null, flag ? 1003 : 1006, pos, 0);
                    }
                }
            }
        }
    }

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World p_149915_1_, IBlockState state) {
		return new TileEntitySecureDoor();
	}
	
	@Override
	public int getMobilityFlag() {
		return 2;
	}
	
	
}