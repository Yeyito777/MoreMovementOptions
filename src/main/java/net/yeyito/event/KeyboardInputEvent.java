package net.yeyito.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyboardInputEvent extends InputUtil {
    public static int tickCounter = 0;
    public static int lastLeftInputTick = -999;
    public static int lastPressedInputTick = -999;
    public static boolean heldLeft = false;
    public static int tickQuicknessAcceptance = 4; // The higher the number the more forgiving, it's in ticks.

    public static boolean preActivation = false;
    public static int preActivationTick = -999;

    public static void keyInputDetection() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            System.out.println(tickCounter-lastPressedInputTick);
            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_A) && tickCounter-lastLeftInputTick < tickQuicknessAcceptance) {
                lastLeftInputTick = -999;
                if (preActivation) {
                    assert client.player != null;
                    client.player.sendChatMessage("dick out",Text.literal("dick out"));
                    preActivation = false;
                } else {
                    preActivation = true;
                    preActivationTick = tickCounter;
                }

            }

            if (preActivation && tickCounter-preActivationTick > tickQuicknessAcceptance*1.5) {
                preActivation = false;
            }

            if (!InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_A) && heldLeft) {
                lastLeftInputTick = tickCounter;
            }

            if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_A) && !heldLeft) {
                lastPressedInputTick = tickCounter;
            }
            heldLeft = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_A);

            //InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_A);

        });
    }
    public static boolean isKeyPressedThisTick(int Key) {
        return true;
    }

    public static boolean isKeyReleasedThisTick(int Key) {
        return true;
    }



    public static void register() {
        keyInputDetection();
    }
}
