package com.julianscode.mobloot.container;

import com.julianscode.mobloot.tileentity.TileEntityBag;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerBag extends Container {

	private TileEntityBag bag;
	
	public ContainerBag(InventoryPlayer ip, TileEntityBag bag) {
		this.bag = bag;
		int index = 0;
		for(int i=0; i<3; i++) {
			for(int j=0; j<9; j++) {
				this.addSlotToContainer(new Slot(ip, index+9, 8+j*18, 41+i*18));
				index++;
			}
		}
		for(int i=0; i<9; i++) {
			this.addSlotToContainer(new Slot(ip, i, 8+i*18, 99));
		}
		for(int i=0; i<9; i++) {
			this.addSlotToContainer(new BagSlot(bag, i, 8+i*18, 18));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
            ItemStack stack = null;
            Slot slotObject = (Slot) inventorySlots.get(slot);

            //null checks and checks if the item can be stacked (maxStackSize > 1)
            if (slotObject != null && slotObject.getHasStack()) {
                    ItemStack stackInSlot = slotObject.getStack();
                    stack = stackInSlot.copy();

                    //merges the item into player inventory since its in the tileEntity
                    if (slot < 9) {
                            if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
                                    return null;
                            }
                    }
                    //places it into the tileEntity is possible since its in the player inventory
                    else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                            return null;
                    }

                    if (stackInSlot.stackSize == 0) {
                            slotObject.putStack(null);
                    } else {
                            slotObject.onSlotChanged();
                    }

                    if (stackInSlot.stackSize == stack.stackSize) {
                            return null;
                    }
                    slotObject.onPickupFromSlot(player, stackInSlot);
            }
            return stack;
    }
}
