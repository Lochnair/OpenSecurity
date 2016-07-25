package pcl.opensecurity.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import pcl.opensecurity.tileentity.TileEntitySecureDoor;

public class BlockSecurityDoorPrivate extends BlockSecurityDoor {

	public BlockSecurityDoorPrivate()
	{
		super();
		float f = 0.5F;
		float f1 = 1.0F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		this.setHardness(400F);
		this.setResistance(6000F);
		this.setUnlocalizedName("securityDoor");
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
}
