package com.sci.torcherina;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TorcherinaRegistry 
{
	public static void blacklistString(String string)
	{
		if (string.indexOf(':') == -1)
		{
			try
			{
		        Class<?> clazz = Torcherina.instance.getClass().getClassLoader().loadClass(string);
		        if(clazz == null)
		        {
		        	Torcherina.logger.info("Class null: " + string);
		            return;
		        }
		        else if(!TileEntity.class.isAssignableFrom(clazz))
		        {
		        	Torcherina.logger.info("Class not a TileEntity: " + string);
		            return;
		        }
		        blacklistTile((Class<? extends TileEntity>) clazz);
		    }
			catch(ClassNotFoundException e)
			{
				Torcherina.logger.info("Class not found: " + string + ", ignoring");
		    }
		}
		else
		{
			String[] parts = string.split(":");
		    if(parts.length != 2)
		    {
		    	Torcherina.logger.info("Received malformed message: " + string);
		    	return;
		    }
		    Block block = Block.REGISTRY.getObject(new ResourceLocation(parts[0], parts[1]));
		    if(block == null)
		    {
		    	Torcherina.logger.info("Could not find block: " + string + ", ignoring");
		        return;
		    }
		    Torcherina.logger.info("Blacklisting block: " + block.getUnlocalizedName());
		    blacklistBlock(block);
		}
	}
    public static void blacklistBlock(Block block)
    {
        TorcherinaRegistry.blacklistedBlocks.add(block);
    }
    public static void blacklistTile(Class<? extends TileEntity> tile)
    {
        TorcherinaRegistry.blacklistedTiles.add(tile);
    }
    public static boolean isBlockBlacklisted(Block block)
    {
        return TorcherinaRegistry.blacklistedBlocks.contains(block);
    }
    public static boolean isTileBlacklisted(Class<? extends TileEntity> tile)
    {
        return TorcherinaRegistry.blacklistedTiles.contains(tile);
    }
    private static Set<Block> blacklistedBlocks = new HashSet<Block>();
    private static Set<Class<? extends TileEntity>> blacklistedTiles = new HashSet<Class<? extends TileEntity>>();
}
