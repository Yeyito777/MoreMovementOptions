package net.yeyito.client;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Animations {
    public static float animFloat(float startKeyFrame,float endKeyframe, double timeStep, double animationTime, int animationType) {
        if (timeStep >= animationTime) {return  endKeyframe;}

        switch (animationType) {
            case AnimationTypes.CUBIC -> {return (float) (startKeyFrame + (3 * Math.pow(timeStep / animationTime, 2) - 2 * Math.pow(timeStep / animationTime, 3)) * (endKeyframe - startKeyFrame));}
            case AnimationTypes.QUADRATIC -> {return (float) (startKeyFrame + (Math.pow(timeStep / animationTime,2)* (endKeyframe - startKeyFrame)));}
            case AnimationTypes.QUARTIC -> {return (float) (startKeyFrame+ (Math.pow(timeStep/ animationTime, 4)) * (endKeyframe- startKeyFrame));}
            case AnimationTypes.QUINTIC -> {return (float) (startKeyFrame+ (Math.pow(timeStep/ animationTime, 5)) * (endKeyframe- startKeyFrame));}
            case AnimationTypes.SINUSOIDAL -> {return (float) (startKeyFrame+ (1 - Math.cos(timeStep/ animationTime * Math.PI))/2 * (endKeyframe- startKeyFrame));}
            case AnimationTypes.EXPONENTIAL -> {return (float) (startKeyFrame+ (Math.pow(2, 10 * (timeStep/ animationTime - 1))) * (endKeyframe- startKeyFrame));}
            case AnimationTypes.CIRCULAR -> {return (float) (startKeyFrame+ (-(Math.sqrt(1 - Math.pow(timeStep/ animationTime, 2))) * (endKeyframe- startKeyFrame)));}
            case AnimationTypes.BACK -> {return (float) (startKeyFrame+ (Math.pow(timeStep/ animationTime, 3) - 2 * Math.pow(timeStep/ animationTime, 2) + Math.pow(timeStep/ animationTime, 1)) * (endKeyframe- startKeyFrame));}
            case AnimationTypes.BOUNCE -> {return (float) (startKeyFrame+ (Math.abs(Math.sin(timeStep/ animationTime * (Math.PI * (timeStep/ animationTime * 1.5 + 1) ) ) + 1 ) * (endKeyframe- startKeyFrame)));}
            case AnimationTypes.ELASTIC -> {return (float) (startKeyFrame+ (Math.pow(2, 10 * (timeStep/ animationTime - 1)) * Math.sin( (timeStep/ animationTime - 0.75) * (2 * Math.PI) / 0.3) + 1) * (endKeyframe- startKeyFrame));}
            case AnimationTypes.LINEAR -> {return (float) (startKeyFrame+ (timeStep/ animationTime) * (endKeyframe- startKeyFrame));}
            case AnimationTypes.SINUSOIDAL_OVERSHOOT -> {return (float) (startKeyFrame+ (1 - Math.cos(timeStep/ animationTime * Math.PI) + 0.1) * (endKeyframe- startKeyFrame));}
            case AnimationTypes.NATURAL -> {return (float) (startKeyFrame+ (Math.pow(timeStep/ animationTime, 2) - Math.pow(timeStep/ animationTime, 3)) * (endKeyframe- startKeyFrame));}
            case AnimationTypes.NATURAL_HURT -> {return (float) (startKeyFrame+ (Math.pow(timeStep/ animationTime, 3) - Math.pow(timeStep/ animationTime, 4)) * (endKeyframe- startKeyFrame));}
            default -> throw new IllegalStateException("Unexpected value: " + animationType);
        }
    }
}
