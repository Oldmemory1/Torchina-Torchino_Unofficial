package com.sci.torcherina.blocks.blocks;

import com.sci.torcherina.blocks.tiles.TileDoubleCompressedTorcherina;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class BlockDoubleCompressedTorcherina extends BlockTorcherina
{
    public BlockDoubleCompressedTorcherina(){this.setUnlocalizedName("torcherina.double_compressed_torcherina");}
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state){return new TileDoubleCompressedTorcherina();}
}