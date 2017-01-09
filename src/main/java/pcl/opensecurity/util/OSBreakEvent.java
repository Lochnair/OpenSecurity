package pcl.opensecurity.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pcl.opensecurity.OpenSecurity;
import pcl.opensecurity.tileentity.TileEntityDoorController;
import pcl.opensecurity.tileentity.TileEntitySecureDoor;

public class OSBreakEvent {
	
	public OSBreakEvent() {
		OpenSecurity.logger.info("Registering BreakEvent");
	}
	
	@SubscribeEvent(priority= EventPriority.NORMAL)
	public void onBlockBreak(BreakEvent event) {
		if (OpenSecurity.registerBlockBreakEvent) {
			TileEntity TE = event.getWorld().getTileEntity(event.getPos());
			if(TE instanceof TileEntitySecureDoor){
				TileEntitySecureDoor xEntity = (TileEntitySecureDoor) TE;
				if(xEntity.getOwner()!=null && !xEntity.getOwner().equals(event.getPlayer().getUniqueID().toString()) && !event.getPlayer().capabilities.isCreativeMode && !xEntity.getOwner().isEmpty()){
					event.setCanceled(true);
				}
			} else if(TE instanceof TileEntityDoorController){
				TileEntityDoorController xEntity = (TileEntityDoorController) TE;
				if(xEntity.getOwner()!=null && !xEntity.getOwner().equals(event.getPlayer().getUniqueID().toString()) && !event.getPlayer().capabilities.isCreativeMode && !xEntity.getOwner().isEmpty()){
					event.setCanceled(true);
				}
			}	
		}
	}
}
