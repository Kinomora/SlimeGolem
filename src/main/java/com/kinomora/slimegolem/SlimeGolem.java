package com.kinomora.slimegolem;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SlimeGolem.ID)
public class SlimeGolem {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ID = "slimegolem";

    public static ResourceLocation createRes(String name) {
        return new ResourceLocation(ID, name);
    }

    public SlimeGolem() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(RegistryHandler.SLIME_LAYER, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.SLIME_BLOCK, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(RegistryHandler.CARVED_MELON_BLOCK, RenderType.getSolid());
    }
}
