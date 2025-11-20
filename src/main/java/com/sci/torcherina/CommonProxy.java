package com.sci.torcherina;

import com.sci.torcherina.blocks.ModBlocks;
import com.sci.torcherina.network.EventHandler;
import com.sci.torcherina.network.PacketHandler;

import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
	public void preInit()
	{
		PacketHandler.preInit();
		ModBlocks.preInit();
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
}
