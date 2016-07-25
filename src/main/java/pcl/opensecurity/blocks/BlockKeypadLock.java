package pcl.opensecurity.blocks;

import pcl.opensecurity.tileentity.TileEntityKeypadLock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockKeypadLock extends BlockOSBase {

	public BlockKeypadLock()
	{
		super();
		setUnlocalizedName("keypadlock");
	}
	
	//@Override
	//@SideOnly(Side.CLIENT)
	//public void registerBlockIcons(IIconRegister iconRegister)
	//{
	//	textureTop=iconRegister.registerIcon("opensecurity:machine_side");
	//	textureBottom=iconRegister.registerIcon("opensecurity:machine_side");
	//	textureSide=iconRegister.registerIcon("opensecurity:machine_side");
	//}  
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{		
		if (side.getIndex()>1)
		{
			int mx=pos.getX(), my=pos.getY(), mz=pos.getZ();
		    switch(side.getIndex())
			{
  			case 2: mz++; break;
			case 3: mz--; break;
			case 4: mx++; break;
			case 5: mx--; break;
			default:
				break;
			}			

		    IBlockState state=worldIn.getBlockState(new BlockPos(mx, my, mz));
			if (state.getProperties().get("facing").equals(side))
				return false;			
		}
			
		return super.shouldSideBeRendered(worldIn,pos,side);
	}
	
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//BLLog.debug("Activate with hit at %f, %f, %f", hitX, hitY, hitZ);
		if (playerIn.isSneaking())
			return false;
		
		//if it wasn't the face, false
		//int facing=worldIn.getBlockMetadata(x, y, z);
		
		if (state.getProperties().get("facing").equals(side.getIndex()))
		{
			//BLLog.debug("wrong side.");
			return false;
		}
		//BLLog.debug("side = %d", side);
		float relX=0f,relY=hitY*16f;
		//normalize face-relative "x" pixel position
		switch(side.getIndex())
		{
		case 2: relX=hitX*16f; break;
		case 3: relX=(1f-hitX)*16f; break;
		case 4: relX=(1f-hitZ)*16f; break;
		case 5: relX=hitZ*16f; break;
		default:
			break;
		}
		
		//figure out what, if any, button was hit?
		if (relX<4f || relX>12 || relY<2f || relY>11.5f)
		{
			//BLLog.debug("outside button area.");			
			//completely outside area of buttons, return
			return true;
		}
		int col=(int)((relX-4f)/3f);
		float colOff=(relX-4f)%3f;
		int row=(int)((relY-2f)/2.5f);
		float rowOff=(relY-2f)%2.5f;
		//check and return if between buttons
		if (colOff>2f || rowOff>2f)
		{
			//BLLog.debug("between buttons.");
			return true;
		}		
		
		//ok! hit a button!
		//BLLog.debug("Hit button on row %d in col %d", row, col);
		int idx = (2-col)+3*(3-row);
		TileEntityKeypadLock te=(TileEntityKeypadLock)worldIn.getTileEntity(pos);
		te.pressedButton(playerIn,idx);
		return true;
	
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityKeypadLock();
	}
}