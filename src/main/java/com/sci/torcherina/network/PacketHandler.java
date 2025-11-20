package com.sci.torcherina.network;

import com.sci.torcherina.Torcherina;

import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
	public static void preInit()
	{
		Torcherina.network.registerMessage(MessageModifierKey.class, MessageModifierKey.class, 0, Side.SERVER);
	}
	public static void sendUpdateToSever(boolean isKeyPressed)
	{
		Torcherina.network.sendToServer(new MessageModifierKey(isKeyPressed));
	}
}
