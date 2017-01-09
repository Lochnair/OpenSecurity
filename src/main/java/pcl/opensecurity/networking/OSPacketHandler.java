package pcl.opensecurity.networking;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import pcl.opensecurity.tileentity.TileEntityKVM;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;



public class OSPacketHandler implements IMessage {
    BlockPos pos;
    int isEnabled = 0;
	int side;

    public OSPacketHandler() {
    }

    public OSPacketHandler(int i, BlockPos pos, int isEnabled) {
        this.side = i;
        this.pos = pos;
        this.isEnabled = isEnabled;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        side = ByteBufUtils.readVarInt(buf, 1);
        pos = new BlockPos(ByteBufUtils.readVarInt(buf, 5),
                ByteBufUtils.readVarInt(buf, 5),
                ByteBufUtils.readVarInt(buf, 5)
        );
        isEnabled = ByteBufUtils.readVarInt(buf, 1);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, side, 1);
        ByteBufUtils.writeVarInt(buf, pos.getX(), 5);
        ByteBufUtils.writeVarInt(buf, pos.getY(), 5);
        ByteBufUtils.writeVarInt(buf, pos.getZ(), 5);
        ByteBufUtils.writeVarInt(buf, isEnabled, 1);
    }


    public static class PacketHandler implements IMessageHandler<OSPacketHandler, IMessage> {

        @Override
        public IMessage onMessage(OSPacketHandler message, MessageContext ctx) {
            TileEntity te = ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(message.pos);
            boolean isEnabled;
            int side = message.side;
            if(message.isEnabled == 1)
            	isEnabled = true;
            else
            	isEnabled = false;
            if(te instanceof TileEntityKVM) {
				((TileEntityKVM) te).setSide(side, isEnabled);
            }
            return null;
        }
    }
}