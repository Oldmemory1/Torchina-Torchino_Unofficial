package com.sci.torcherina.blocks.blocks;

import com.sci.torcherina.blocks.tiles.TileCompressedTorcherina;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class BlockCompressedLanterina extends BlockLanterina
{
    public BlockCompressedLanterina(){this.setUnlocalizedName("torcherina.compressed_lanterina");}
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state){return new TileCompressedTorcherina();}
}