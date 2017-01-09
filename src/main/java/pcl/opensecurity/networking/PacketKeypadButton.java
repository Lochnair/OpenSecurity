package pcl.opensecurity.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketKeypadButton implements IMessage {
	int button;
	short id;
	short instance;
	int dimension;
	BlockPos pos;
	
	public PacketKeypadButton() {
		//intentionally empty
	}
	
	public PacketKeypadButton(short instance, int dim, BlockPos pos, int button) {
		this.id = 1;
		this.instance = instance;
		this.dimension = dim;
		this.pos = pos;
		this.button = button;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(button);
		buf.writeShort(instance);
		buf.writeInt(dimension);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		button = buf.readInt();
		instance = buf.readShort();
		dimension = buf.readInt();
		pos = new BlockPos(buf.readInt(),
				buf.readInt(),
				buf.readInt()
		);
	}
}
