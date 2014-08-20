package com.julianscode.mobloot.blocks;



import com.julianscode.mobloot.MobLoot;
import com.julianscode.mobloot.tileentity.TileEntityBag;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BlockBag extends BlockContainer {
	
	protected BlockBag(Material p_i45397_1_) {
		super(p_i45397_1_);
	}

	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (w.isRemote)
        {
            return true;
        }
        else
        {
        	IInventory iinventory = (IInventory) w.getTileEntity(x, y, z);
            if (iinventory != null)
            {
                FMLNetworkHandler.openGui(player, MobLoot.instance, MobLoot.bagGuiID, w, x, y, z);
            }

            return true;
        }
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		TileEntityBag teb = new TileEntityBag();
		return teb;
	}
	@SideOnly(Side.CLIENT)
    private IIcon[] icons;
   
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
          icons = new IIcon[2];
          icons[0] = par1IconRegister.registerIcon(MobLoot.MODID+":bag_sides");
          icons[1] = par1IconRegister.registerIcon(MobLoot.MODID+":bag_top");
    }
   
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
    	if(par1==1) {
    		return icons[1];
    	} else {
    		return icons[0];
    	}
    }
}
