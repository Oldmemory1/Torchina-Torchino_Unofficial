package com.sci.torcherina;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter.Result;

import com.sci.torcherina.blocks.ModBlocks;
import com.sci.torcherina.blocks.tiles.TileCompressedTorcherina;
import com.sci.torcherina.blocks.tiles.TileDoubleCompressedTorcherina;
import com.sci.torcherina.blocks.tiles.TileTorcherina;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid=Torcherina.MOD_ID, name=Torcherina.MOD_NAME, version="7.8", acceptedMinecraftVersions="[1.12,1.12.2]", useMetadata=true, dependencies = "before:carryon")
public class Torcherina 
{
	public static boolean logPlacement;
	public static boolean overPoweredRecipe;
	public static boolean compressedTorcherina;
	public static boolean doubleCompressedTorcherina;
	public static Configuration config;
	public static HashMap<EntityPlayer, Boolean> keyStates = new HashMap<EntityPlayer, Boolean>();
	public static Logger logger;
	public static SimpleNetworkWrapper network;
	public static final String MOD_ID = "torcherina";
	public static final String MOD_NAME = "Torcherina";
	private static String[] blacklistedBlocks;
	private static String[] blacklistedTiles;

	@Mod.Instance(Torcherina.MOD_ID)
	public static Torcherina instance;
	
	@SidedProxy(clientSide="com.sci.torcherina.ClientProxy", serverSide="com.sci.torcherina.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void construction(FMLConstructionEvent event)
	{
		// Because carryon wont add torcherina to the default config
		// TODO: look into using reflection.
		if(Loader.isModLoaded("carryon"))
		{
			File carryOnConfigFile = new File(Loader.instance().getConfigDir(), "carryon.cfg");
			if(!carryOnConfigFile.exists())
			{
				String[] forbiddenTiles = new String[]
		    	{
		    		"minecraft:end_portal",
		    		"minecraft:end_gateway",
		    		"minecraft:double_plant",
		    		"minecraft:bed",
		    		"minecraft:wooden_door",
		    		"minecraft:iron_door",
		    		"minecraft:spruce_door",
		    		"minecraft:birch_door",
		    		"minecraft:jungle_door",
		    		"minecraft:acacia_door",
		    		"minecraft:dark_oak_door",
		    		"minecraft:waterlily",
		    		"minecraft:cake",
		    		"animania:block_trough",
		    		"animania:block_invisiblock",
		    		"colossalchests:*",
		    		"ic2:*",
		    		"bigreactors:*",
		    		"forestry:*",
		    		"tconstruct:*",
		    		"rustic:*",
		    		"botania:*",
		    		"astralsorcery:*",
		    		"quark:colored_bed_*",
		    		"immersiveengineering:*",
		    		"embers:block_furnace",
		    		"embers:ember_bore",
		    		"embers:ember_activator",
		    		"embers:mixer",
		    		"embers:heat_coil",
		    		"embers:large_tank",
		    		"embers:crystal_cell",
		    		"embers:alchemy_pedestal",
		    		"embers:boiler",
		    		"embers:combustor",
		    		"embers:catalzyer",
		    		"embers:field_chart",
		    		"embers:inferno_forge",
		    		"storagedrawers:framingtable",
		    		"skyresources:*",
		    		"lootbags:*",
		    		"exsartagine:*",
		    		"aquamunda:tank",
		    		"torcherina:*"
		    	};
				try
				{
					carryOnConfigFile.createNewFile();
					Configuration carryonConfig = new Configuration(carryOnConfigFile);
					carryonConfig.load();
					Property forbiddenTilesProp = carryonConfig.get("general.blacklist", "forbiddenTiles", forbiddenTiles);
					carryonConfig.save();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				Configuration carryonConfig = new Configuration(carryOnConfigFile);
				try
				{
					carryonConfig.load();
			        Property forbiddenTiles = carryonConfig.getCategory("general.blacklist").get("forbiddenTiles");
			        String[] newValues = forbiddenTiles.getStringList();
			        boolean found = false;
			        for(String name: newValues)
			        {
			        	if(name == "torcherina:*")
			        	{
			        		found = true;
			        		break;
			        	}
			        }
			        if(!found)
			        {
			        	StringBuilder sb = new StringBuilder();
			        	sb.append("torcherina:*");
			        	for(String name : newValues) sb.append(","+name);
			        	newValues = sb.toString().split("[,]");
			        	forbiddenTiles.set(newValues);
			        }
			    }
				finally
				{
			        if(carryonConfig.hasChanged()) carryonConfig.save();
			    }
			}
		}
	}
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Torcherina.MOD_NAME);
		File folder = new File(event.getModConfigurationDirectory(), "sci4me");
        if(!folder.exists()) folder.mkdir();
        Configuration config = new Configuration(new File(folder, Torcherina.MOD_NAME+".cfg"));
		try
		{
	        config.load();
	        logPlacement = config.getBoolean("logPlacement", "general", false, "(For Server Owners) Is it logged when someone places a Torcherina?");
	        overPoweredRecipe = config.getBoolean("overPoweredRecipe", "general", true, "Is the recipe for Torcherina extremely OP?");
	        compressedTorcherina = config.getBoolean("compressedTorcherina", "general", true, "Is the recipe for the Compressed Torcherina enabled?");
	        doubleCompressedTorcherina = config.getBoolean("doubleCompressedTorcherina", "general", true, "Is the recipe for the Double Compressed Torcherina enabled? Only takes effect if Compressed Torcherinas are enabled.");
	        blacklistedBlocks = config.getStringList("blacklistedBlocks", "blacklist", new String[]{}, "modid:unlocalized");
	        blacklistedTiles = config.getStringList("blacklistedTiles", "blacklist", new String[]{}, "Fully qualified class name");
	    }
		finally
		{
	        if(config.hasChanged()) config.save();
	    }
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		logger.info(TileTorcherina.class);
		TorcherinaRegistry.blacklistBlock(Blocks.AIR);
		TorcherinaRegistry.blacklistBlock(Blocks.WATER);
		TorcherinaRegistry.blacklistBlock(Blocks.FLOWING_WATER);
		TorcherinaRegistry.blacklistBlock(Blocks.LAVA);
		TorcherinaRegistry.blacklistBlock(Blocks.FLOWING_LAVA);
		TorcherinaRegistry.blacklistBlock(ModBlocks.torcherina);
		TorcherinaRegistry.blacklistBlock(ModBlocks.compressedTorcherina);
		TorcherinaRegistry.blacklistBlock(ModBlocks.doubleCompressedTorcherina);
		TorcherinaRegistry.blacklistBlock(ModBlocks.lanterina);
		TorcherinaRegistry.blacklistBlock(ModBlocks.compressedLanterina);
		TorcherinaRegistry.blacklistBlock(ModBlocks.doubleCompressedLanterina);
		TorcherinaRegistry.blacklistTile(TileTorcherina.class);
		TorcherinaRegistry.blacklistTile(TileCompressedTorcherina.class);
		TorcherinaRegistry.blacklistTile(TileDoubleCompressedTorcherina.class);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if(Loader.isModLoaded("projecte"))
		{
			TorcherinaRegistry.blacklistString("projecte:dm_pedestal");
		}
		for(String block : blacklistedBlocks)
		{
			TorcherinaRegistry.blacklistString(block);
		}
        for(String tile : blacklistedTiles)
        {
        	TorcherinaRegistry.blacklistString(tile);
        }
	}
	
	@Mod.EventHandler
    public void imcMessage(FMLInterModComms.IMCEvent event)
	{
        for(FMLInterModComms.IMCMessage message : event.getMessages())
        {
            if(!message.isStringMessage())
            {
                logger.info("Received non-string message! Ignoring");
                continue;
            }
            String s = message.getStringValue();
            TorcherinaRegistry.blacklistString(s);
        }
    }
}
