package pcl.opensecurity.tileentity;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import pcl.opensecurity.OpenSecurity;
import pcl.opensecurity.client.sounds.AlarmResource;
import pcl.opensecurity.client.sounds.ISoundTile;

public class TileEntityAlarm extends TileEntityMachineBase implements SimpleComponent, ISoundTile {
	public static String cName = "OSAlarm";
	public Boolean shouldPlay = false;
	public String soundName = "klaxon1";
	public float volume = 1.0F;
	public Boolean computerPlaying = false;

	public TileEntityAlarm() {
		super();
		setSound(soundName);
	}

	@Override
	public String getComponentName() {
		return "os_alarm";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public boolean shouldPlaySound() {
		return shouldPlay;
	}

	@Override
	public String getSoundName() {
		return soundName;
	}

	@Override
	public float getVolume() {
		return volume;
	}

	@Override
	public ResourceLocation setSound(String sound) {
		setSoundRes(new ResourceLocation(OpenSecurity.MODID + ":" + sound));
		return getSoundRes();
	}

	public void setShouldStart(boolean b) {
		worldObj.markBlockForUpdate(pos);
		getDescriptionPacket();
		shouldPlay = true;

	}

	public void setShouldStop(boolean b) {
		shouldPlay = false;
		worldObj.markBlockForUpdate(pos);
		getDescriptionPacket();
	}

	// OC Methods.

	@Callback
	public Object[] greet(Context context, Arguments args) {
		return new Object[] { "Lasciate ogne speranza, voi ch'intrate" };
	}

	@Callback(doc = "function(range:integer):string; Sets the range in blocks of the alarm", direct = true)
	public Object[] setRange(Context context, Arguments args) {
		Float newVolume = (float) args.checkInteger(0);
		if (newVolume >= 15 && newVolume <= 150) {
			volume = newVolume / 15 + 0.5F;
			return new Object[] { "Success" };
		} else {
			return new Object[] { "Error, range should be between 15-150" };
		}
	}

	@Callback(doc = "function(soundName:string):string; Sets the alarm sound", direct = true)
	public Object[] setAlarm(Context context, Arguments args) {
		String alarm = args.checkString(0);
		if (AlarmResource.sound_map.containsValue(alarm + ".ogg")) {
			soundName = alarm;
			setSound(alarm);
			worldObj.markBlockForUpdate(pos);
			getDescriptionPacket();
			return new Object[] { "Success" };
		} else {
			return new Object[] { "Fail" };
		}
	}

	@Callback(doc = "function():string; Activates the alarm", direct = true)
	public Object[] activate(Context context, Arguments args) {
		this.setShouldStart(true);
		computerPlaying = true;
		return new Object[] { "Ok" };
	}

	@Callback(doc = "function():table; Returns a table of Alarm Sounds", direct = true)
	public Object[] listSounds(Context context, Arguments args) {
		return new Object[] { OpenSecurity.alarmList };
	}

	@Callback(doc = "function(int:x, int:y, int:z, string:sound, float:range(1-10 recommended)):string; Plays sound at x y z", direct = true)
	public Object[] playSoundAt(Context context, Arguments args) {
		if (OpenSecurity.enableplaySoundAt) {
			int x = args.checkInteger(0);
			int y = args.checkInteger(1);
			int z = args.checkInteger(2);
			String sound = args.checkString(3);
			float range = args.checkInteger(4);
			worldObj.playSoundEffect(x, y, z, sound, range / 15 + 0.5F, 1.0F);
			return new Object[] { "Ok" };
		} else {
			return new Object[] { "Disabled" };
		}
	}

	@Callback(doc = "function():string; Deactivates the alarm", direct = true)
	public Object[] deactivate(Context context, Arguments args) {
		this.setShouldStop(true);
		computerPlaying = false;
		return new Object[] { "Ok" };
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new S35PacketUpdateTileEntity(this.pos, 1, tagCom);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound tagCom = packet.getNbtCompound();
		this.readFromNBT(tagCom);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		readSyncableDataFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		writeSyncableDataToNBT(tag);
	}

	private void readSyncableDataFromNBT(NBTTagCompound tag) {
		shouldPlay = tag.getBoolean("isPlayingSound");
		soundName = tag.getString("alarmName");
		volume = tag.getFloat("volume");
		computerPlaying = tag.getBoolean("computerPlaying");
	}

	private void writeSyncableDataToNBT(NBTTagCompound tag) {
		tag.setBoolean("isPlayingSound", shouldPlay);
		tag.setString("alarmName", soundName);
		tag.setFloat("volume", volume);
		tag.setBoolean("computerPlaying", computerPlaying);
	}

	@Override
	public boolean playSoundNow() {
		// TODO Auto-generated method stub
		return false;
	}
}
