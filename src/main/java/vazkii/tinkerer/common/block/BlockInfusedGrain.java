package vazkii.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.ItemInfusedSeeds;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.registry.ITTinkererBlock;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;

/**
 * Created by pixlepix on 4/14/14.
 */
public class BlockInfusedGrain extends BlockCrops implements ITTinkererBlock {

	Aspect aspect;

	public BlockInfusedGrain(Aspect aspect) {
		super();
		this.aspect = aspect;
	}

	public BlockInfusedGrain() {
		this(Aspect.FIRE);
	}

	private IIcon[] icons;

	//Code based off vanilla potato code
	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta < 7) {
			if (meta == 6) {
				meta = 5;
			}
			return this.icons[meta >> 1];
		} else {
			return this.icons[3];
		}
	}

	public static BlockInfusedGrain getBlockFromAspect(Aspect aspect) {
		for (Block block : ThaumicTinkerer.registry.getBlockFromClass(BlockInfusedGrain.class)) {
			if (((BlockInfusedGrain) block).aspect == aspect) {
				return (BlockInfusedGrain) block;
			}
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.icons = new IIcon[4];

		for (int i = 0; i < this.icons.length; ++i) {
			this.icons[i] = p_149651_1_.registerIcon("potatoes" + "_stage_" + i);
		}
	}

	@Override
	public int damageDropped(int p_149692_1_) {
		return ItemInfusedSeeds.getMetaForAspect(aspect);
	}

	protected Item func_149866_i() {
		return ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedSeeds.class);
	}

	protected Item func_149865_P() {
		return Items.wheat;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			if (item != null) {
				ret.add(new ItemStack(item, 1, damageDropped(metadata)));
			}
		}
		if (metadata >= 7) {
			for (int i = 0; i < 3 + fortune; ++i) {
				if (world.rand.nextInt(15) <= metadata) {
					ret.add(new ItemStack(this.func_149866_i(), 1, damageDropped(metadata)));
				}
			}
		}

		return ret;
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(Aspect.WATER);
		result.add(Aspect.AIR);
		result.add(Aspect.EARTH);
		return result;
	}

	@Override
	public String getBlockName() {
		if (aspect == Aspect.AIR) {
			return LibBlockNames.INFUSED_GRAIN_AIR;
		}
		if (aspect == Aspect.EARTH) {
			return LibBlockNames.INFUSED_GRAIN_EARTH;
		}
		if (aspect == Aspect.WATER) {
			return LibBlockNames.INFUSED_GRAIN_WATER;
		}
		return LibBlockNames.INFUSED_GRAIN_FIRE;
	}

	@Override
	public boolean shouldRegister() {
		return true;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return false;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlock() {
		return null;
	}

	@Override
	public Class<? extends TileEntity> getTileEntity() {
		return null;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return null;
	}
}