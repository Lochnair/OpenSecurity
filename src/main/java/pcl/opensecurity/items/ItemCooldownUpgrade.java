package pcl.opensecurity.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Caitlyn
 *
 */
public class ItemCooldownUpgrade extends Item implements IItemColor {

	public ItemCooldownUpgrade() {
		super();
		setUnlocalizedName("cooldownUpgrade");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemstack(ItemStack stack, int par2)
	{
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("color")) {
			return stack.getTagCompound().getInteger("color");	
		} else {
			return 0xFFFFFF;
		}
	}
}
