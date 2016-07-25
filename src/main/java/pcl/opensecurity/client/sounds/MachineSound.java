package pcl.opensecurity.client.sounds;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;

/**
 * @author SleepyTrousers, Vexatos, lifted from Computronics, will rewrite
 *         soon(tm)
 */
public class MachineSound extends PositionedSound implements ITickableSound {

	private boolean donePlaying;

	public MachineSound(ResourceLocation sound, float x, float y, float z, float volume, float pitch) {
		this(sound, x, y, z, volume, pitch, true);
	}

	public MachineSound(ResourceLocation sound, float x, float y, float z, float volume, float pitch, boolean repeat) {
		super(sound);
		this.xPosF = x;
		this.yPosF = y;
		this.zPosF = z;
		this.volume = volume;
		this.pitch = pitch;
		this.repeat = repeat;
	}

	@Override
	public void update() {
	}

	public void endPlaying() {
		donePlaying = true;
	}

	public void startPlaying() {
		donePlaying = false;
	}

	@Override
	public boolean isDonePlaying() {
		return donePlaying;
	}
}