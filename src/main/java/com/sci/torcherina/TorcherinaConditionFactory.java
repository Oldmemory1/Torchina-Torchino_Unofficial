package com.sci.torcherina;

import com.google.gson.JsonObject;
import java.util.function.BooleanSupplier;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class TorcherinaConditionFactory implements IConditionFactory
{
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json)
	{
		boolean value = JsonUtils.getBoolean(json , "value", true);
		String key = JsonUtils.getString(json, "type");
		if(key.equals(Torcherina.MOD_ID+":"+"torcherina_overpowered")) return () -> Torcherina.overPoweredRecipe == value;
		else if(key.equals(Torcherina.MOD_ID+":"+"compressed_torcherina_enabled")) return () -> Torcherina.compressedTorcherina == value;
		else if(key.equals(Torcherina.MOD_ID+":"+"double_compressed_torcherina_enabled")) return () -> Torcherina.doubleCompressedTorcherina == value;
		return () -> false;
	}
}