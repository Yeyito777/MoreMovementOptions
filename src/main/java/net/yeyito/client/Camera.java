package net.yeyito.client;

import org.jetbrains.annotations.Nullable;

public class Camera {
    private static float ZRotation = 0;
    private static float XRotation = 0;
    private static float YRotation = 0;

    private static final int X_ASSIGNED_NUMBER = 0;
    private static final int Y_ASSIGNED_NUMBER = 1;
    private static final int Z_ASSIGNED_NUMBER = 2;

    private static final double DEAFULT_ANIMATION_TIME_SECONDS = 0.1;
    private static final int DEAFULT_ANIMATION_STYLE = AnimationTypes.SINUSOIDAL;

    private static final double[] systemTimesSeconds = new double[3];
    private static final double[] animationTimesSeconds = new double[3];
    private static final float[] targetValues = new float[3];
    private static final float[] startingValues = new float[3];
    private static final boolean[] returnToStartingRotValues = new boolean[3];
    private static final int[] animationStylesRot = new int[3];

    public static void worldRenderTick() {
        double systemTimeSecondsNow = System.nanoTime()/1e+9;

        double secondsSinceAnimXStart = systemTimeSecondsNow - systemTimesSeconds[X_ASSIGNED_NUMBER];
        double secondsSinceAnimYStart = systemTimeSecondsNow - systemTimesSeconds[Y_ASSIGNED_NUMBER];
        double secondsSinceAnimZStart = systemTimeSecondsNow - systemTimesSeconds[Z_ASSIGNED_NUMBER];

        double XAnimationTime = animationTimesSeconds[X_ASSIGNED_NUMBER];
        double YAnimationTime = animationTimesSeconds[Y_ASSIGNED_NUMBER];
        double ZAnimationTime = animationTimesSeconds[Z_ASSIGNED_NUMBER];

        if (((Float) startingValues[X_ASSIGNED_NUMBER]).isNaN()) {startingValues[X_ASSIGNED_NUMBER] = 0;}
        if (((Float) startingValues[Y_ASSIGNED_NUMBER]).isNaN()) {startingValues[Y_ASSIGNED_NUMBER] = 0;}
        if (((Float) startingValues[Z_ASSIGNED_NUMBER]).isNaN()) {startingValues[Z_ASSIGNED_NUMBER] = 0;}

        if (secondsSinceAnimXStart >= XAnimationTime && returnToStartingRotValues[X_ASSIGNED_NUMBER]) {setXRotation(0,XAnimationTime,false, DEAFULT_ANIMATION_STYLE);} else {
            XRotation =  Animations.animFloat(startingValues[X_ASSIGNED_NUMBER],targetValues[X_ASSIGNED_NUMBER],secondsSinceAnimXStart,XAnimationTime,animationStylesRot[X_ASSIGNED_NUMBER]);
        }
        if (secondsSinceAnimYStart >= YAnimationTime && returnToStartingRotValues[Y_ASSIGNED_NUMBER]) {setYRotation(0,YAnimationTime,false, DEAFULT_ANIMATION_STYLE);} else {
            YRotation =  Animations.animFloat(startingValues[Y_ASSIGNED_NUMBER],targetValues[Y_ASSIGNED_NUMBER],secondsSinceAnimYStart,YAnimationTime,animationStylesRot[Y_ASSIGNED_NUMBER]);
        }
        if (secondsSinceAnimZStart >= ZAnimationTime && returnToStartingRotValues[Z_ASSIGNED_NUMBER]) {setZRotation(0,ZAnimationTime,false, DEAFULT_ANIMATION_STYLE);} else {
            ZRotation =  Animations.animFloat(startingValues[Z_ASSIGNED_NUMBER],targetValues[Z_ASSIGNED_NUMBER],secondsSinceAnimZStart,ZAnimationTime,animationStylesRot[Z_ASSIGNED_NUMBER]);
        }
    }

    public static void setXRotation(float XRotationFinal, @Nullable Double AnimationTimeSeconds, boolean returnToStartingRot, @Nullable Integer animStyle) {
        startingValues[X_ASSIGNED_NUMBER] = XRotation;
        targetValues[X_ASSIGNED_NUMBER] = XRotationFinal;
        returnToStartingRotValues[X_ASSIGNED_NUMBER] = returnToStartingRot;

        if (AnimationTimeSeconds == null) {AnimationTimeSeconds = DEAFULT_ANIMATION_TIME_SECONDS;}
        if (animStyle == null) {animStyle = DEAFULT_ANIMATION_STYLE;}

        animationStylesRot[X_ASSIGNED_NUMBER] = animStyle;
        systemTimesSeconds[X_ASSIGNED_NUMBER] = System.nanoTime()/1e+9;
        animationTimesSeconds[X_ASSIGNED_NUMBER] = AnimationTimeSeconds;
    }
    public static void setYRotation(float YRotationFinal, @Nullable Double AnimationTimeSeconds, boolean returnToStartingRot, @Nullable Integer animStyle) {
        startingValues[Y_ASSIGNED_NUMBER] = YRotation;
        targetValues[Y_ASSIGNED_NUMBER] = YRotationFinal;
        returnToStartingRotValues[Y_ASSIGNED_NUMBER] = returnToStartingRot;

        if (AnimationTimeSeconds == null) {AnimationTimeSeconds = DEAFULT_ANIMATION_TIME_SECONDS;}
        if (animStyle == null) {animStyle = DEAFULT_ANIMATION_STYLE;}

        animationStylesRot[Y_ASSIGNED_NUMBER] = animStyle;
        systemTimesSeconds[Y_ASSIGNED_NUMBER] = System.nanoTime()/1e+9;
        animationTimesSeconds[Y_ASSIGNED_NUMBER] = AnimationTimeSeconds;
    }
    public static void setZRotation(float ZRotationFinal, @Nullable Double AnimationTimeSeconds, boolean returnToStartingRot, @Nullable Integer animStyle) {
        startingValues[Z_ASSIGNED_NUMBER] = ZRotation;
        targetValues[Z_ASSIGNED_NUMBER] = ZRotationFinal;
        returnToStartingRotValues[Z_ASSIGNED_NUMBER] = returnToStartingRot;

        if (AnimationTimeSeconds == null) {AnimationTimeSeconds = DEAFULT_ANIMATION_TIME_SECONDS;}
        if (animStyle == null) {animStyle = DEAFULT_ANIMATION_STYLE;}

        animationStylesRot[Z_ASSIGNED_NUMBER] = animStyle;
        systemTimesSeconds[Z_ASSIGNED_NUMBER] = System.nanoTime()/1e+9;
        animationTimesSeconds[Z_ASSIGNED_NUMBER] = AnimationTimeSeconds;
    }

    public static float getZRotation() {
        if (((Float) ZRotation).isNaN() || ((Float) ZRotation).isInfinite()) {
            return 0;
        }
        return ZRotation;
    }
    public static float getXRotation() {
        if (((Float) XRotation).isNaN() || ((Float) XRotation).isInfinite()) {
            return 0;
        }
        return XRotation;
    }
    public static float getYRotation() {
        if (((Float) YRotation).isNaN() || ((Float) YRotation).isInfinite()) {
            return 0;
        }
        return YRotation;
    }
}
