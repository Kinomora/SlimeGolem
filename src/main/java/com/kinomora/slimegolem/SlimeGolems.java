package com.kinomora.slimegolem;

import com.kinomora.slimegolem.config.ModConfig;
import com.kinomora.slimegolem.entity.SlimeGolemEntity;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SlimeGolems.ID)
public class SlimeGolems {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ID = "slimegolem";

    public static ResourceLocation createRes(String name) {
        return new ResourceLocation(ID, name);
    }

    public SlimeGolems() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addEntityAttributes);

        ForgeConfigSpec.Builder  configBuilder = new ForgeConfigSpec.Builder();
        ModConfig.init(configBuilder);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, configBuilder.build());
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.SLIME_LAYER, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.SLIME_BLOCK, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegistryHandler.CARVED_MELON_BLOCK, RenderType.solid());
    }

    private void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(RegistryHandler.SLIME_GOLEM, SlimeGolemEntity.attributes().build());
    }
}
