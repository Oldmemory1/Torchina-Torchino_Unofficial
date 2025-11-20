package com.sci.torcherina;

import com.sci.torcherina.blocks.ModBlocks;
import com.sci.torcherina.network.EventHandler;
import com.sci.torcherina.network.KeyHandler;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();
		ModBlocks.initRenders();
		KeyHandler.preInit();
		
	}
}
