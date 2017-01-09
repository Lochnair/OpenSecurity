package pcl.opensecurity.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLocation {
	
	/** The IBlockAccess for this block location. */
	public final IBlockAccess blockAccess;
	/** The World for this block location. <br>
	 *  May be null if the IBlockAccess getter was used. */
	public final World world;
	/** The location for this block location. */
	public final BlockPos pos;
	
	private final boolean isWorld;
	
	private BlockLocation(IBlockAccess blockAccess, World world,
	                      BlockPos pos, boolean isWorld) {
		this.blockAccess = blockAccess;
		this.world = world;
		this.pos = pos;
		this.isWorld = isWorld;
	}
	
	// Static instantiation methods

	/** Returns a block location from this world and location. */
	public static BlockLocation get(IBlockAccess blockAccess, BlockPos pos) {
		return new BlockLocation(blockAccess, null, pos, false);
	}
	/** Returns a block location from this world and location. */
	public static BlockLocation get(World world, BlockPos pos) {
		return new BlockLocation(world, world, pos, true);
	}
	/** Returns a block location from the tile entity's location. */
	public static BlockLocation get(TileEntity tileEntity) {
		return get(tileEntity.getWorld(), tileEntity.getPos());
	}
	
	// Relative instantiation methods
	
	/** Returns a block location with its location set relative to this one. */
	public BlockLocation relative(int x, int y, int z) {
		return new BlockLocation(blockAccess, world, pos.add(x, y, z), isWorld);
	}
	
	/** Returns a block location for the block in this direction, this many blocks away. */
	public BlockLocation offset(EnumFacing facing, int distance) {
		return relative(facing.getFrontOffsetX() * distance,
						facing.getFrontOffsetY() * distance,
						facing.getFrontOffsetZ() * distance);
	}
	
	/** Returns a block location for the neighbor block in this direction. */
	public BlockLocation neighbor(EnumFacing facing) {
		return relative(facing.getFrontOffsetX(), facing.getFrontOffsetY(), facing.getFrontOffsetZ());
	}
	
	/** Returns a block location for the block to the west (-X). */
	public BlockLocation west() { return neighbor(EnumFacing.WEST); }
	/** Returns a block location for the block to the east (+X). */
	public BlockLocation east() { return neighbor(EnumFacing.EAST); }
	/** Returns a block location for the block below (-Y). */
	public BlockLocation below() { return neighbor(EnumFacing.DOWN); }
	/** Returns a block location for the block above (+Y). */
	public BlockLocation above() { return neighbor(EnumFacing.UP); }
	/** Returns a block location for the block to the north (-Z). */
	public BlockLocation north() { return neighbor(EnumFacing.NORTH); }
	/** Returns a block location for the block to the south (+Z). */
	public BlockLocation south() { return neighbor(EnumFacing.SOUTH); }
	
	// Getting and setting
	
	/** Gets the block at this location. */
	public Block getBlock() { return blockAccess.getBlockState(pos).getBlock(); }

	/** Gets the tile entity of the block at this location. */
	public TileEntity getTileEntity() { return blockAccess.getTileEntity(pos); }
	
	/** Gets the tile entity of the block at this location.
	 *  Returns null if the tile entity is not the correct type. */
	@SuppressWarnings("unchecked")
	public <T extends TileEntity> T getTileEntity(Class<T> tileEntityClass) {
		TileEntity tileEntity = getTileEntity();
		return tileEntityClass.isInstance(tileEntity) ? (T)tileEntity : null;
	}
	
	/** Gets the tile entity of the block at this location.
	 *  Throws an error if there is no tile entity or it's not the correct type. */
	@SuppressWarnings("unchecked")
	public <T extends TileEntity> T getTileEntityStrict(Class<T> tileEntityClass) {
		TileEntity tileEntity = getTileEntity();
		if (tileEntity == null)
			throw new Error(String.format("Expected tile entity at %s, but none found.", this));
		if (!tileEntityClass.isInstance(tileEntity))
			throw new Error(String.format("Expected tile entity at %s to be '%s', but found '%s' instead.",
			                              this, tileEntityClass.getName(), tileEntity.getClass().getName()));
		return (T)tileEntity;
	}
	
	/** Sets the block at this location. */
	public void setBlock(Block block) {
		if (isWorld) world.setBlockState(pos, block.getBlockState().getBaseState(), 0);
	}
	
	/** Sets the tile entity for the block at this location. <br>
	 *  <b>Warning:</b> This is usually done automatically.
	 *                  Only use this when you know what you're doing! */
	public void setTileEntity(TileEntity tileEntity) {
		if (isWorld) world.setTileEntity(pos, tileEntity);
	}
	
	// Additional block related methods
	
	/** Returns if the block is air. */
	public boolean isAir() {
		return blockAccess.isAirBlock(pos);
	}
	/** Returns if the block is replaceable, like tall grass or fluids. */
	public boolean isReplaceable() {
		return getBlock().isReplaceable(blockAccess, pos);
	}
	/** Returns if the block is solid on that side. */
	public boolean isSideSolid(EnumFacing side) {
		return blockAccess.isSideSolid(pos, side, false);
	}
	
	// Additional tile entity related methods
	
	
	
	// Equals, hashCode and toString
	
	@Override
	public boolean equals(Object obj) {
		BlockLocation loc;
		return ((obj instanceof BlockLocation) &&
		        (blockAccess == (loc = (BlockLocation)obj).blockAccess) &&
		        pos.equals(loc.pos));
	}
	
	@Override
	public int hashCode() {
		return blockAccess.hashCode() ^ pos.getX() ^ (pos.getZ() << 4) ^ (pos.getY() << 8);
	}
	
	@Override
	public String toString() {
		return String.format("[%s; Coords=%s,%s,%s]", getWorldName(), pos.getX(), pos.getY(), pos.getZ());
	}
	
	// Helper functions
	
	private String getWorldName() {
		return isWorld ? ("DIM=" + Integer.toString(world.provider.getDimension()))
		                : blockAccess.toString();
	}
	
}