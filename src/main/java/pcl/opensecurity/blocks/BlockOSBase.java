package pcl.opensecurity.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class BlockOSBase extends BlockContainer {

	protected BlockOSBase() {
		super(Material.iron);
		this.setHardness(5F);
		this.setResistance(30F);
		setDefaultState(blockState.getBaseState().withProperty(active, false).withProperty(facing, EnumFacing.NORTH));
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	public static final IProperty<EnumFacing> facing = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final IProperty<Boolean> active = PropertyBool.create("active");
    
    @Override
    protected BlockState createBlockState() {
        BlockState blockState = new BlockState(this, active, facing);
        return blockState;
    }
    
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing face = EnumFacing.getHorizontal((meta >> 1) & 0x3);
		return getDefaultState().withProperty(active, (meta & 1 ) == 1).withProperty(facing, face);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = (state.getValue(active) ? 1 : 0);
		meta |= (state.getValue(facing).getHorizontalIndex()) << 1;
		return meta;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		BlockPos diff = pos.subtract(placer.getPosition());
		EnumFacing face = EnumFacing.getFacingFromVector(diff.getX(), 0, diff.getZ()).getOpposite();
		worldIn.setBlockState(pos, state.withProperty(facing, face));
	}

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        worldIn.setBlockState(pos, state.withProperty(active, false));
    }
	
	public boolean onBlockEventReceived(World world, BlockPos pos, int eventId, int eventPramater) {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    }
	
}
