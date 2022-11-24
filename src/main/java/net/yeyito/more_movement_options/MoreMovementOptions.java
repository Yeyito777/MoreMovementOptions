package net.yeyito.more_movement_options;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.yeyito.event.server.ServerTickEvent;
import net.yeyito.networking.PacketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreMovementOptions implements ModInitializer {
	public static final String MOD_ID = "more_movement_options";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String VERSION = "v0.0.1";

	@Override
	public void onInitialize() {
		LOGGER.info(MOD_ID + " version " + VERSION);

		PacketHandler.registerC2SPackets();
		ServerTickEvent.register();
	}
}
