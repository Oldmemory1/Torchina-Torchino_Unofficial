package com.sci.torcherina.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sci.torcherina.Torcherina;
import com.sci.torcherina.blocks.blocks.BlockCompressedLanterina;
import com.sci.torcherina.blocks.blocks.BlockCompressedTorcherina;
import com.sci.torcherina.blocks.blocks.BlockDoubleCompressedLanterina;
import com.sci.torcherina.blocks.blocks.BlockDoubleCompressedTorcherina;
import com.sci.torcherina.blocks.blocks.BlockLanterina;
import com.sci.torcherina.blocks.blocks.BlockTorcherina;
import com.sci.torcherina.blocks.tiles.TileCompressedTorcherina;
import com.sci.torcherina.blocks.tiles.TileDoubleCompressedTorcherina;
import com.sci.torcherina.blocks.tiles.TileTorcherina;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Torcherina.MOD_ID)
public final class ModBlocks{
    public static BlockTorcherina torcherina;
    public static BlockTorcherina compressedTorcherina;
    public static BlockTorcherina doubleCompressedTorcherina;
    public static BlockLanterina lanterina;
    public static BlockLanterina compressedLanterina;
    public static BlockLanterina doubleCompressedLanterina;
    public static void preInit()
    {
        torcherina = new BlockTorcherina();
        compressedTorcherina = new BlockCompressedTorcherina();
        doubleCompressedTorcherina = new BlockDoubleCompressedTorcherina();
        lanterina = new BlockLanterina();
        compressedLanterina = new BlockCompressedLanterina();
        doubleCompressedLanterina = new BlockDoubleCompressedLanterina();
        ForgeRegistries.BLOCKS.register(torcherina.setRegistryName("blocktorcherina"));
        ForgeRegistries.BLOCKS.register(compressedTorcherina.setRegistryName("blockcompressedtorcherina"));
        ForgeRegistries.BLOCKS.register(doubleCompressedTorcherina.setRegistryName("blockdoublecompressedtorcherina"));
        ForgeRegistries.BLOCKS.register(lanterina.setRegistryName("blocklanterina"));
        ForgeRegistries.BLOCKS.register(compressedLanterina.setRegistryName("blockcompressedlanterina"));
        ForgeRegistries.BLOCKS.register(doubleCompressedLanterina.setRegistryName("blockdoublecompressedlanterina"));
        ForgeRegistries.ITEMS.register(new ItemBlock(torcherina).setRegistryName(torcherina.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemBlock(compressedTorcherina).setRegistryName(compressedTorcherina.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemBlock(doubleCompressedTorcherina).setRegistryName(doubleCompressedTorcherina.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemBlock(lanterina).setRegistryName(lanterina.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemBlock(compressedLanterina).setRegistryName(compressedLanterina.getRegistryName()));
        ForgeRegistries.ITEMS.register(new ItemBlock(doubleCompressedLanterina).setRegistryName(doubleCompressedLanterina.getRegistryName()));
        // For 1.12 - 1.12.2 compat
        GameRegistry.registerTileEntity(TileTorcherina.class, "torcherina_tile");
        GameRegistry.registerTileEntity(TileCompressedTorcherina.class, "compressed_torcherina_tile");
        GameRegistry.registerTileEntity(TileDoubleCompressedTorcherina.class, "double_compressed_torcherina_tile");
        // Todo: enable in 1.13.x
        //GameRegistry.registerTileEntity(TileTorcherina.class, new ResourceLocation(Torcherina.MOD_ID, "torcherina_tile"));
        //GameRegistry.registerTileEntity(TileCompressedTorcherina.class, new ResourceLocation(Torcherina.MOD_ID, "compressed_torcherina_tile"));
        //GameRegistry.registerTileEntity(TileDoubleCompressedTorcherina.class, new ResourceLocation(Torcherina.MOD_ID, "double_compressed_torcherina_tile"));
    }
    public static void initRenders()
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(torcherina), 0, new ModelResourceLocation(new ResourceLocation(Torcherina.MOD_ID, "blocktorcherina"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(compressedTorcherina), 0, new ModelResourceLocation(new ResourceLocation(Torcherina.MOD_ID, "blockcompressedtorcherina"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(doubleCompressedTorcherina), 0, new ModelResourceLocation(new ResourceLocation(Torcherina.MOD_ID, "blockdoublecompressedtorcherina"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(lanterina), 0, new ModelResourceLocation(new ResourceLocation(Torcherina.MOD_ID, "blocklanterina"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(compressedLanterina), 0, new ModelResourceLocation(new ResourceLocation(Torcherina.MOD_ID, "blockcompressedlanterina"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(doubleCompressedLanterina), 0, new ModelResourceLocation(new ResourceLocation(Torcherina.MOD_ID, "blockdoublecompressedlanterina"), "inventory"));
    }
}
