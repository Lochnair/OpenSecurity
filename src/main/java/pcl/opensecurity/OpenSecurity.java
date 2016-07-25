package pcl.opensecurity;

import java.io.File;
import java.net.URL;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pcl.opensecurity.gui.OSGUIHandler;
import pcl.opensecurity.networking.HandlerKeypadButton;
import pcl.opensecurity.networking.OSPacketHandler;
import pcl.opensecurity.networking.PacketBoltFire;
import pcl.opensecurity.networking.PacketKeypadButton;
import pcl.opensecurity.networking.OSPacketHandler.PacketHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = OpenSecurity.MODID, name = "OpenSecurity", version = BuildInfo.versionNumber + "." + BuildInfo.buildNumber, dependencies = "required-after:OpenComputers")
public class OpenSecurity {

	public static final String MODID = "opensecurity";
	public static File alarmSounds;


	@Instance(value = MODID)
	public static OpenSecurity instance;

	@SidedProxy(clientSide = "pcl.opensecurity.ClientProxy", serverSide = "pcl.opensecurity.CommonProxy")
	public static CommonProxy proxy;
	public static Config cfg = null;

	public static boolean debug = false;
	public static int rfidRange;
	public static boolean enableplaySoundAt = false;
	public static boolean ignoreUUIDs = false;
	public static boolean registerBlockBreakEvent = true;

	public static final Logger logger = LogManager.getFormatterLogger(MODID);

	public static List<String> alarmList;

	
	
	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		long time = System.nanoTime();
		cfg = new Config(new Configuration(event.getSuggestedConfigurationFile()));
		alarmSounds = new File("./mods/OpenSecurity/sounds/alarms/");
		System.out.println(alarmSounds);
		alarmList = cfg.alarmsConfigList;
		rfidRange = cfg.rfidMaxRange;
		enableplaySoundAt = cfg.enableplaySoundAt;
		ignoreUUIDs = cfg.ignoreUUIDs;
		registerBlockBreakEvent = cfg.registerBlockBreak;

		if ((event.getSourceFile().getName().endsWith(".jar") || debug) && event.getSide().isClient() && cfg.enableMUD) {
			logger.info("Registering mod with OpenUpdater");
			try {
				Class.forName("pcl.mud.OpenUpdater").getDeclaredMethod("registerMod", ModContainer.class, URL.class, URL.class).invoke(null, FMLCommonHandler.instance().findContainerFor(this), new URL("http://PC-Logix.com/OpenSecurity/get_latest_build.php?mcver=1.7.10"), new URL("http://PC-Logix.com/OpenSecurity/changelog.php?mcver=1.7.10"));
			} catch (Throwable e) {
				logger.info("OpenUpdater is not installed, not registering.");
			}
		}
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new OSGUIHandler());
	    network = NetworkRegistry.INSTANCE.newSimpleChannel("OpenSecurity");
	    int packetID = 0;
	    network.registerMessage(PacketHandler.class, OSPacketHandler.class, packetID++, Side.SERVER);
	    network.registerMessage(PacketBoltFire.class, PacketBoltFire.class, packetID++, Side.CLIENT);
	    network.registerMessage(HandlerKeypadButton.class, PacketKeypadButton.class, packetID++, Side.CLIENT);
	    logger.info("Registered " + packetID + " packets");
	    ContentRegistry.preInit();
	    logger.info("Finished pre-init in %d ms", (System.nanoTime() - time) / 1000000);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		long time = System.nanoTime();
		proxy.registerRenderers();
		proxy.registerSounds();
		ContentRegistry.init();
		logger.info("Finished init in %d ms", (System.nanoTime() - time) / 1000000);
	}
}