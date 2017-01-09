package pcl.opensecurity.networking;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pcl.opensecurity.OpenSecurity;
import pcl.opensecurity.tileentity.TileEntityKeypadLock;

public class HandlerKeypadButton implements IMessageHandler<PacketKeypadButton, IMessage> {

	@Override
	public IMessage onMessage(PacketKeypadButton message, MessageContext ctx) {
		short instanceID = message.id;
		int dim = message.dimension;
		
		//BLLog.debug("Keypad packet at %d, %d, %d in dim %d", x, y, z, dim);
		
		World world = OpenSecurity.proxy.getWorld(dim);
		
		if (world != null) {
			TileEntity te=world.getTileEntity(message.pos);
			
			int button = message.button;
			//BLLog.debug("Got button for button # %d", button);
			
			TileEntityKeypadLock tek=(TileEntityKeypadLock)te;
			tek.buttonStates[button].press(te.getWorld().getTotalWorldTime());
		}
		return null;
	}

}