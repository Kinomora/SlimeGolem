package com.kinomora.slimegolem;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class ModConfig {

    //Config Options
    public BooleanValue enableRockyGolem;
    public BooleanValue enableIronyGolem;
    public BooleanValue enableRainDamage;
    public BooleanValue alternateLayers;

    private static ModConfig instance;

    public static ModConfig get() {
        return instance;
    }

    public static void init(Builder builder) {
        instance = new ModConfig(builder);
    }

    public ModConfig(Builder builder){
        builder.comment("Slime Golems").push("Types");
        enableRockyGolem = builder.define("enableRockyGolems", true);
        enableIronyGolem = builder.define("enableSlimyIronGolems", false);
        builder.pop();

        builder.comment("Features").push("Misc");
        enableRainDamage = builder.define("golemsHurtByWet",false);
        alternateLayers = builder.define("layersOnlyInSlimeChunksOrSwamps", false);
        builder.pop();
    }
}
