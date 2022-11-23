package net.yeyito.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.yeyito.movement.Dashing;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardInputEvent extends InputUtil {
    public static int tickCounter = 0;
    public static int tickQuicknessAcceptance = 3; // The higher the number the more forgiving, it's in ticks.

    public static int[] Keys = {GLFW.GLFW_KEY_A, GLFW_KEY_D, GLFW.GLFW_KEY_W, GLFW_KEY_S}; // Lists the available keys
    public static List<Integer> KeysButItsAList = new ArrayList<>();
    public static List<Boolean> WasKeyHeldLastTickReg = new ArrayList<>();
    public static List<Integer> LastTickKeyWasPressedReg = new ArrayList<>();
    public static List<Integer> LastTickKeyWasHeldReg = new ArrayList<>();

    public static void codeRanBeforeKeyUpdates(MinecraftClient client) {
        if (client != null) {
            if (checkIfKeyIsPressedThisTick(GLFW_KEY_A) && getTickDifferenceToCurrentTick(LastTickKeyWasPressedReg.get(KeysButItsAList.indexOf(GLFW_KEY_A))) <= tickQuicknessAcceptance) {Dashing.dashPlayer(client,"Left");}
            if (checkIfKeyIsPressedThisTick(GLFW_KEY_D) && getTickDifferenceToCurrentTick(LastTickKeyWasPressedReg.get(KeysButItsAList.indexOf(GLFW_KEY_D))) <= tickQuicknessAcceptance) {Dashing.dashPlayer(client,"Right");}
            if (checkIfKeyIsPressedThisTick(GLFW_KEY_W) && getTickDifferenceToCurrentTick(LastTickKeyWasPressedReg.get(KeysButItsAList.indexOf(GLFW_KEY_W))) <= tickQuicknessAcceptance) {Dashing.dashPlayer(client,"Forward");}
            if (checkIfKeyIsPressedThisTick(GLFW_KEY_S) && getTickDifferenceToCurrentTick(LastTickKeyWasPressedReg.get(KeysButItsAList.indexOf(GLFW_KEY_S))) <= tickQuicknessAcceptance) {Dashing.dashPlayer(client,"Backward");}
        }
    }


    public static void keyInputDetection() {
        keyRegistrySetup();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;
            updateAllKeys(client);
        });
    }

    public static void keyRegistrySetup() {
        for (int Key : Keys) {
            KeysButItsAList.add(Key);
            LastTickKeyWasPressedReg.add(-999);
            LastTickKeyWasHeldReg.add(-999);
            WasKeyHeldLastTickReg.add(false);
        }
    }

    public static void updateAllKeys(MinecraftClient client) {
        codeRanBeforeKeyUpdates(client);

        for (int Key : Keys) {
            updateKey(Key);
        }
    }
    public static void  updateKey(int Key) {
        isKeyPressedThisTick(Key);
        isKeyReleasedThisTick(Key);
        WasKeyHeldLastTickReg.set(KeysButItsAList.indexOf(Key),InputUtil.isKeyPressed(getWindow(), Key));
    }

    public static boolean isKeyPressedThisTick(int Key) {
        if (InputUtil.isKeyPressed(getWindow(), Key) && !WasKeyHeldLastTickReg.get(KeysButItsAList.indexOf(Key))) {
            LastTickKeyWasPressedReg.set(KeysButItsAList.indexOf(Key),tickCounter);
            return true;
        }
        return false;
    }

    public static boolean isKeyReleasedThisTick(int Key) {
        if (!InputUtil.isKeyPressed(getWindow(), Key) && WasKeyHeldLastTickReg.get(KeysButItsAList.indexOf(Key))) {
            LastTickKeyWasHeldReg.set(KeysButItsAList.indexOf(Key),tickCounter);
            return true;
        }
        return false;
    }

    public static boolean checkIfKeyIsPressedThisTick (int Key) {
        return InputUtil.isKeyPressed(getWindow(), Key) && !WasKeyHeldLastTickReg.get(KeysButItsAList.indexOf(Key));
    }

    public static boolean checkIfKeyIsReleasedThisTick(int Key) {
        return !InputUtil.isKeyPressed(getWindow(), Key) && WasKeyHeldLastTickReg.get(KeysButItsAList.indexOf(Key));
    }

    public static int getTickDifferenceToCurrentTick(int Tick) {
        return tickCounter - Tick;
    }
    public static long getWindow() {
        return MinecraftClient.getInstance().getWindow().getHandle();
    }
    public static void register() {
        keyInputDetection();
    }
}
