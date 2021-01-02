package com.kinomora.slimegolem;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class ModConfig {

    //Config Options
    public BooleanValue enableRockyGolem;
    public BooleanValue enableIronyGolem;

    private static ModConfig instance;

    public static ModConfig get() {
        return instance;
    }

    public static void init(Builder builder) {
        instance = new ModConfig(builder);
    }

    public ModConfig(Builder builder){
        builder.comment("Slime Golems Features").push("Golem Types");
        enableRockyGolem = builder.define("enableRockyGolem", true);
        enableIronyGolem = builder.define("enableIronyGolem", false);
        builder.pop();
    }
}
