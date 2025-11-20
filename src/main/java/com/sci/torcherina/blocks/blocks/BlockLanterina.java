package com.sci.torcherina.blocks.blocks;

import com.sci.torcherina.Torcherina;
import com.sci.torcherina.blocks.tiles.TileTorcherina;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLanterina extends BlockPumpkin
{
	public BlockLanterina()
	{
		this.setHardness(1.0F);
		this.setSoundType(SoundType.WOOD);
        this.setLightLevel(1.0F);
        this.setUnlocalizedName("torcherina.lanterina");
    }
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        if(!world.isRemote)
        {
            TileEntity tile = world.getTileEntity(pos);
            if(tile != null && tile instanceof TileTorcherina) ((TileTorcherina) tile).setPoweredByRedstone(world.isBlockIndirectlyGettingPowered(pos) > 0);
        }
        super.onBlockAdded(world, pos, state);
        if(Torcherina.logPlacement) Torcherina.logger.info(this.getClass().getName().substring(30) + " was placed at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
    }
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        if(!world.isRemote)
        {
            TileEntity tile = world.getTileEntity(pos);
            if(tile != null && tile instanceof TileTorcherina) ((TileTorcherina) tile).setPoweredByRedstone(world.isBlockIndirectlyGettingPowered(pos) > 0);
        }
        super.neighborChanged(state, world, pos, block, fromPos);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state){return true;}
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state){return new TileTorcherina();}
}