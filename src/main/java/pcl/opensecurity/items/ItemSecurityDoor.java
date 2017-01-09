package pcl.opensecurity.items;

import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import pcl.opensecurity.ContentRegistry;
import pcl.opensecurity.tileentity.TileEntitySecureDoor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSecurityDoor extends ItemDoor {
	public Block doorBlock;

	public ItemSecurityDoor(Block block) {
		super(block);
		this.doorBlock = block;
		this.maxStackSize = 16;
		this.setUnlocalizedName("securityDoor");
		this.setCreativeTab(ContentRegistry.CreativeTab);
	}

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != null)
        {
            return EnumActionResult.FAIL;
        }
        else {
            pos.add(0, 1, 0);
            Block block;

            block = ContentRegistry.SecurityDoorBlock;

            if (playerIn.canPlayerEdit(pos, facing, stack) && playerIn.canPlayerEdit(pos.add(0, 1, 0), facing, stack)) {
                if (!block.canPlaceBlockAt(worldIn, pos)) {
                    return EnumActionResult.FAIL;
                } else {
                    int i1 = MathHelper.floor_double((double) ((playerIn.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(worldIn, pos, i1, block, playerIn);
                    --stack.stackSize;
                    return EnumActionResult.PASS;
                }
            } else {
                return EnumActionResult.FAIL;
            }
        }
    }

    public static void placeDoorBlock(World world, BlockPos pos, int direction, Block block, EntityPlayer entityPlayer)
    {
        byte b0 = 0;
        byte b1 = 0;

        if (direction == 0)
        {
            b1 = 1;
        }

        if (direction == 1)
        {
            b0 = -1;
        }

        if (direction == 2)
        {
            b1 = -1;
        }

        if (direction == 3)
        {
            b0 = 1;
        }

        int i1 = (world.getBlock(x - b0, y, z - b1).isNormalCube() ? 1 : 0) + (world.getBlock(x - b0, y + 1, z - b1).isNormalCube() ? 1 : 0);
        int j1 = (world.getBlock(x + b0, y, z + b1).isNormalCube() ? 1 : 0) + (world.getBlock(x + b0, y + 1, z + b1).isNormalCube() ? 1 : 0);
        boolean flag = world.getBlock(x - b0, y, z - b1) == block || world.getBlock(x - b0, y + 1, z - b1) == block;
        boolean flag1 = world.getBlock(x + b0, y, z + b1) == block || world.getBlock(x + b0, y + 1, z + b1) == block;
        boolean flag2 = false;

        if (flag && !flag1)
        {
            flag2 = true;
        }
        else if (j1 > i1)
        {
            flag2 = true;
        }

        world.setBlock(x, y, z, block, direction, 2);
        world.setBlock(x, y + 1, z, block, 8 | (flag2 ? 1 : 0), 2);
        TileEntitySecureDoor tile = (TileEntitySecureDoor) world.getTileEntity(x, y, z);
        tile.setOwner(entityPlayer.getUniqueID().toString());
        TileEntitySecureDoor tileTop = (TileEntitySecureDoor) world.getTileEntity(x, y + 1, z);
        tileTop.setOwner(entityPlayer.getUniqueID().toString());
        world.notifyBlocksOfNeighborChange(x, y, z, block);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, block);
    }
}