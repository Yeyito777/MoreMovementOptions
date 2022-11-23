package net.yeyito.more_movement_options;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.yeyito.Items.DashingItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreMovementOptions implements ModInitializer {
	public static final String MOD_ID = "more_movement_options";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String VERSION = "v0.0.1";

	@Override
	public void onInitialize() {
		LOGGER.info(MOD_ID + " version " + VERSION);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dash_left"), new DashingItem(new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dash_right"), new DashingItem(new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dash_forward"), new DashingItem(new FabricItemSettings().group(ItemGroup.MISC)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dash_backward"), new DashingItem(new FabricItemSettings().group(ItemGroup.MISC)));
	}
}
