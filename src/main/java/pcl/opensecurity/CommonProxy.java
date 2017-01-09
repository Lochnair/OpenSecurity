/**
 * 
 */
package pcl.opensecurity;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import pcl.opensecurity.containers.CardWriterContainer;
import pcl.opensecurity.tileentity.TileEntityRFIDReader;
import pcl.opensecurity.tileentity.TileEntityCardWriter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Caitlyn
 *
 */
public class CommonProxy implements IGuiHandler {

	public void registerRenderers() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te != null && te instanceof TileEntityRFIDReader) {
			TileEntityCardWriter icte = (TileEntityCardWriter) te;
			return new CardWriterContainer(player.inventory, icte);
		} else {
			return null;
		}
	}

	public World getWorld(int dimId) {
		//overridden separately for client and server.
		return null;
	}
	
	public void preInit(FMLPreInitializationEvent e) {

	}

	public void registerSounds() {
		// TODO Auto-generated method stub
		
	}
}
