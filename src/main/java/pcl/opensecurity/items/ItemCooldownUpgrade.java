package pcl.opensecurity.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Caitlyn
 *
 */
public class ItemCooldownUpgrade extends Item {

	public ItemCooldownUpgrade() {
		super();
		setUnlocalizedName("cooldownUpgrade");
		setTextureName("opensecurity:cooldownUpgrade");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int par2)
	{
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("color")) {
			return stack.getTagCompound().getInteger("color");	
		} else {
			return 0xFFFFFF;
		}
	}
}
