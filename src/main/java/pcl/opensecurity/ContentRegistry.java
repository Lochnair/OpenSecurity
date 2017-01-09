/**
 * 
 */
package pcl.opensecurity;

import li.cil.oc.api.fs.FileSystem;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import pcl.opensecurity.blocks.*;
import pcl.opensecurity.client.CreativeTab;
import pcl.opensecurity.drivers.RFIDReaderCardDriver;
import pcl.opensecurity.entity.EntityEnergyBolt;
import pcl.opensecurity.items.*;
import pcl.opensecurity.tileentity.*;
import pcl.opensecurity.util.OSBreakEvent;

import java.util.concurrent.Callable;

/**
 * @author Caitlyn
 *
 */
@Mod.EventBusSubscriber
public class ContentRegistry {

	public static Block magCardReaderBlock;
	public static Block rfidCardReaderBlock;
	public static Block cardWriterBlock;
	public static Block AlarmBlock;
	public static Block EntityDetectorBlock;
	public static Block SecurityDoorBlock;
	public static Block DoorControllerBlock;
	public static Block DataBlock;
	public static Block SwitchableHubBlock;
	public static Block KVMBlock;
	public static Block SecurityDoorPrivateBlock;
	public static Block DisplayPanelBlock;
	public static Block energyTurretBlock;
	public static Block keypadLockBlock;
	public static Block biometricScanner;
	public static Item magCardItem;
	public static Item rfidCardItem;
	public static Item securityDoorItem;
	public static Item securityDoorPrivateItem;
	public static Item rfidReaderCardItem;
	public static Item secureNetworkCardItem;
	public static Item damageUpgradeItem;
	public static Item cooldownUpgradeItem;
	public static Item energyUpgradeItem;
	public static Item movementUpgradeItem;
	public static ItemBlock securityitemBlock;
	public static ItemStack secureOS_disk;
	public static CreativeTabs CreativeTab;
	
	
    // Called on mod preinit()
	public static void preInit() {
        registerTabs();
        registerBlocks();
        registerEntities();
        registerItems();
        registerEvents();
        registerRecipes();
	}
	
	public static void init() {
		li.cil.oc.api.Driver.add(new RFIDReaderCardDriver());
		li.cil.oc.api.Driver.add((li.cil.oc.api.driver.Item) secureNetworkCardItem);
	}

	private static void registerEntities() {
		EntityRegistry.registerModEntity(EntityEnergyBolt.class, "energybolt", 0, OpenSecurity.instance, 64, 20, true);
	}

	private static void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new OSBreakEvent());
		OpenSecurity.logger.info("Registered Events");
	}

	private static void registerBlock(Block block, String unlocalizedName) {
		GameRegistry.register(block.setCreativeTab(CreativeTab).setRegistryName(unlocalizedName));
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	private static void registerItem(Item item, String unlocalizedName) {
		GameRegistry.register(item.setCreativeTab(CreativeTab).setRegistryName(unlocalizedName));
	}
	
	private static void registerBlocks() {
		magCardReaderBlock = new BlockMagReader();
		registerBlock(magCardReaderBlock, "magreader");
		GameRegistry.registerTileEntity(TileEntityMagReader.class, "MagCardTE");

		rfidCardReaderBlock = new BlockRFIDReader();
		registerBlock(rfidCardReaderBlock, "rfidcardreader");
		GameRegistry.registerTileEntity(TileEntityRFIDReader.class, "RFIDTE");

		cardWriterBlock = new BlockCardWriter();
		registerBlock(cardWriterBlock, "rfidwriter");
		GameRegistry.registerTileEntity(TileEntityCardWriter.class, "RFIDWriterTE");

		AlarmBlock = new BlockAlarm();
		registerBlock(AlarmBlock, "alarm");
		GameRegistry.registerTileEntity(TileEntityAlarm.class, "AlarmTE");

		EntityDetectorBlock = new BlockEntityDetector();
		registerBlock(EntityDetectorBlock, "entitydetector");
		GameRegistry.registerTileEntity(TileEntityEntityDetector.class, "EntityDetectorTE");

		DoorControllerBlock = new BlockDoorController();
		registerBlock(DoorControllerBlock, "doorcontroller");
		GameRegistry.registerTileEntity(TileEntityDoorController.class, "DoorControllerTE");

		SecurityDoorBlock = new BlockSecurityDoor();
		registerBlock(SecurityDoorBlock, "SecurityDoor");

		SecurityDoorPrivateBlock = new BlockSecurityDoorPrivate();
		registerBlock(SecurityDoorPrivateBlock, "SecurityDoorPrivate");

		GameRegistry.registerTileEntity(TileEntitySecureDoor.class, "SecureDoorTE");

		DataBlock = new BlockData();
		registerBlock(DataBlock, OpenSecurity.MODID + ".DataBlock");
		GameRegistry.registerTileEntity(TileEntityDataBlock.class, OpenSecurity.MODID + ".DataBlockTE");

		SwitchableHubBlock = new BlockSwitchableHub();
		registerBlock(SwitchableHubBlock, OpenSecurity.MODID + ".SwitchableHub");
		GameRegistry.registerTileEntity(TileEntitySwitchableHub.class, OpenSecurity.MODID + ".SwitchableHubTE");

		KVMBlock = new BlockKVM();
		registerBlock(KVMBlock, OpenSecurity.MODID + ".BlockKVM");
		GameRegistry.registerTileEntity(TileEntityKVM.class, OpenSecurity.MODID + ".KVMTE");

		//DisplayPanel = new BlockDisplayPanel();
		//registerBlock(DisplayPanel, OpenSecurity.MODID + ".DisplayPanel");
		//DisplayPanel.setCreativeTab(CreativeTab);

		//GameRegistry.registerTileEntity(TileEntityDisplayPanel.class, OpenSecurity.MODID + ".DisplayPanelTE");

		energyTurretBlock = new BlockEnergyTurret();
		registerBlock(energyTurretBlock, "energyTurretBlock");

		GameRegistry.registerTileEntity(TileEntityEnergyTurret.class, "EnergyTurret");

		keypadLockBlock = new BlockKeypadLock();
		registerBlock(keypadLockBlock, "keypadLock");
		GameRegistry.registerTileEntity(TileEntityKeypadLock.class, "KeypadLock");

		biometricScanner = new BlockBiometricReader();
		registerBlock(biometricScanner, "biometricScanner");
		GameRegistry.registerTileEntity(TileEntityBiometricReader.class, "BiometricReader");

		OpenSecurity.logger.info("Registered Blocks");
	}

	private static void registerItems() {
		magCardItem = new ItemMagCard();
		registerItem(magCardItem, "magCard");

		rfidCardItem = new ItemRFIDCard();
		registerItem(rfidCardItem, "rfidCard");

		rfidReaderCardItem = new ItemRFIDReaderCard();
		registerItem(rfidReaderCardItem, "rfidReaderCard");

		secureNetworkCardItem = new ItemSecureNetworkCard();
		registerItem(secureNetworkCardItem, "secureNetworkCard");

		securityDoorItem = new ItemSecurityDoor(SecurityDoorBlock);
		registerItem(securityDoorItem, "securityDoor");

		securityDoorPrivateItem = new ItemSecurityDoorPrivate(SecurityDoorBlock);
		registerItem(securityDoorPrivateItem, "securityDoorPrivate");

		damageUpgradeItem = new ItemDamageUpgrade();
		registerItem(damageUpgradeItem, "damageUpgrade");

		cooldownUpgradeItem = new ItemCooldownUpgrade();
		registerItem(cooldownUpgradeItem, "cooldownUpgrade");

		energyUpgradeItem = new ItemEnergyUpgrade();
		registerItem(energyUpgradeItem, "energyUpgrade");

		movementUpgradeItem = new ItemMovementUpgrade();
		registerItem(movementUpgradeItem, "movementUpgrade");

		OpenSecurity.logger.info("Registered Items");
	}

	private static void registerTabs() {
		 CreativeTab = new CreativeTab("OpenSecurity");
	}
	
	private static void registerRecipes() {
		Callable<FileSystem> SOSFactory = new Callable<FileSystem>() {
			@Override
			public FileSystem call() {
				return li.cil.oc.api.FileSystem.fromClass(OpenSecurity.class, OpenSecurity.MODID, "/lua/SecureOS/SecureOS/");
			}
		};
		secureOS_disk = li.cil.oc.api.Items.registerFloppy("SecureOS", EnumDyeColor.byDyeDamage(1), SOSFactory);

		// Vanilla Minecraft blocks/items
		String iron = "ingotIron";
		String diamond = "gemDiamond";
		String redstone = "dustRedstone";
		String obsidian = "obsidian";
		String glass = "blockGlassColorless";
		String stone = "blockStone";
		ItemStack stone_button = new ItemStack(Blocks.STONE_BUTTON);
		ItemStack paper = new ItemStack(Items.PAPER);
		ItemStack noteblock = new ItemStack(Blocks.NOTEBLOCK);
		ItemStack door = new ItemStack(Items.IRON_DOOR);
		ItemStack gunpowder = new ItemStack(Items.GUNPOWDER);
		ItemStack arrow = new ItemStack(Items.ARROW);
		ItemStack piston = new ItemStack(Item.getItemFromBlock(Blocks.PISTON));
		ItemStack water = new ItemStack(Items.WATER_BUCKET);

		// Opencomputers blocks/items
		String t2microchip = "oc:circuitChip2";
		String t1microchip = "oc:circuitChip1";
		String t1ram = "oc:ram1";
		String pcb = "oc:materialCircuitBoardPrinted";
		String controlunit = "oc:materialCU";
		String wlancard = "oc:wlanCard";
		String cardbase = "oc:materialCard";
		String cable = "oc:cable";
		String transistor = "oc:materialTransistor";
		String numpad = "oc:materialNumPad";
		String batteryUpgrade = "oc:batteryUpgrade1";
		String oc_relay = "oc:relay";
		ItemStack floppy = li.cil.oc.api.Items.get("floppy").createItemStack(1);
		ItemStack datacard;
		if (li.cil.oc.api.Items.get("dataCard").createItemStack(1) != null) {
			datacard = li.cil.oc.api.Items.get("dataCard").createItemStack(1);
		} else {
			datacard = li.cil.oc.api.Items.get("dataCard1").createItemStack(1);
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rfidReaderCardItem, 1),
				"MRM", 
				" N ", 
				"BC ", 
				'M', t2microchip, 'R', t1ram, 'N', wlancard, 'B', cardbase, 'C', controlunit));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EntityDetectorBlock, 1),
				"MRM", 
				"   ", 
				"BC ", 
				'M', t2microchip, 'R', t1ram, 'B', cardbase, 'C', controlunit));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rfidCardReaderBlock, 1),
				" R ", 
				"PFT", 
				" C ", 
				'F', rfidReaderCardItem, 'P', pcb, 'R', redstone, 'C', cable, 'T', t2microchip));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DataBlock, 1),
				" D ", 
				"PFT", 
				" C ", 
				'D', datacard, 'P', pcb, 'R', redstone, 'C', cable, 'T', t2microchip));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AlarmBlock, 1),
				" R ", 
				"PNC", 
				" T ", 
				'N', noteblock, 'P', pcb, 'R', redstone, 'C', cable, 'T', t2microchip));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cardWriterBlock, 1),
				"TRT", 
				"SUS", 
				"PC ", 
				'P', pcb, 'C', cable, 'T', t2microchip, 'S', transistor, 'U', controlunit, 'R', t1ram));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magCardReaderBlock, 1),
				"T T", 
				"S S", 
				"PC ", 
				'P', pcb, 'C', cable, 'T', t2microchip, 'S', transistor));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rfidCardItem, 6),
				"P P", 
				" S ", 
				"PTP", 
				'P', paper, 'S', transistor, 'T', t1microchip));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magCardItem, 6),
				"P P", 
				" S ", 
				"P P", 
				'P', paper, 'S', transistor));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(securityDoorItem, 1),
				"TGT", 
				"ODO", 
				"SOS", 
				'G', glass, 'D', door, 'S', transistor, 'T', t2microchip, 'O', obsidian));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(securityDoorPrivateItem, 1),
				"TOT", 
				"ODO", 
				"SOS", 
				'D', door, 'S', transistor, 'T', t2microchip, 'O', obsidian));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DoorControllerBlock, 1),
				"TOT", 
				"OCO", 
				"SBS", 
				'B', cable, 'C', controlunit, 'S', transistor, 'T', t2microchip, 'O', obsidian));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(SwitchableHubBlock, 1),
				"TBT", 
				"BSB", 
				"RBR", 
				'B', cable, 'S', oc_relay, 'R', transistor, 'T', t2microchip, 'O', obsidian));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(KVMBlock, 1),
				" B ", 
				"BSB", 
				"RBR", 
				'B', cable,  'S', oc_relay, 'R', transistor, 'T', t2microchip, 'O', obsidian));
		
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(energyTurretBlock, 1),
        		"ABA",
        		"BCB",
        		"ABA",
        		'A', iron, 'B', t2microchip, 'C', diamond));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(damageUpgradeItem, 1),
        		"A A",
        		" G ",
        		"A A",
        		'A', arrow, 'G', gunpowder));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(movementUpgradeItem, 1),
        		"R R",
        		" P ",
        		"R R",
        		'P', piston, 'R', redstone));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cooldownUpgradeItem, 1),
        		"R R",
        		" W ",
        		"R R",
        		'W', water, 'R', redstone));
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(energyUpgradeItem, 1),
        		"R R",
        		" B ",
        		"R R",
        		'B', batteryUpgrade, 'R', redstone));
        
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(keypadLockBlock, 1),
				"TIT", 
				"INI",
				"ICI", 
				'T', transistor, 'N', numpad, 'C', t1microchip, 'I', iron));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(biometricScanner, 1),
				"SIS", 
				"STS",
				"SCS", 
				'T', transistor, 'N', numpad, 'C', t1microchip, 'I', iron, 'S', stone));

		GameRegistry.addShapelessRecipe(secureOS_disk, floppy, magCardItem);
		OpenSecurity.logger.info("Registered Recipes");
	}
}
