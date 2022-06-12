package com.kinomora.slimegolem;

import com.kinomora.slimegolem.block.CarvedMelonBlock;
import com.kinomora.slimegolem.block.SlimeBlock;
import com.kinomora.slimegolem.block.SlimeLayerBlock;
import com.kinomora.slimegolem.entity.SlimeGolemEntity;
import com.kinomora.slimegolem.entity.SlimeballEntity;
import com.kinomora.slimegolem.render.SlimeGolemRenderer;
import com.kinomora.slimegolem.render.model.SlimeGolemModel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

//bus variable says this class is use for registering and subscribing to the event bus
@Mod.EventBusSubscriber(modid = SlimeGolems.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {


    public static class Blocks {
        public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, SlimeGolems.ID);

        public static final RegistryObject<Block> SLIME_LAYER = BLOCK_REGISTRY.register("slime_layer", SlimeLayerBlock::new);
        public static final RegistryObject<Block> SLIME_BLOCK = BLOCK_REGISTRY.register("slime_block", SlimeBlock::new);
        public static final RegistryObject<Block> CARVED_MELON = BLOCK_REGISTRY.register("carved_melon_block", CarvedMelonBlock::new);
    }

    public static class Items {
        public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SlimeGolems.ID);

        public static final RegistryObject<Item> SLIME_BLOCK = ITEM_REGISTRY.register(Blocks.SLIME_BLOCK.getId().getPath(), () -> new BlockItem(Blocks.SLIME_BLOCK.get(), new Item.Properties()));
        public static final RegistryObject<Item> CARVED_MELON = ITEM_REGISTRY.register(Blocks.CARVED_MELON.getId().getPath(), () -> new BlockItem(Blocks.CARVED_MELON.get(), new Item.Properties()));
        public static final RegistryObject<Item> SLIME_GOLEM_SPAWN_EGG = ITEM_REGISTRY.register("slime_golem_spawn_egg", () -> new ForgeSpawnEggItem(Entities.SLIME_GOLEM::get, 0x2ed941, 0x8fed5c, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    }

    public static class Entities {
        public static final DeferredRegister<EntityType<?>> ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, SlimeGolems.ID);

        public static final RegistryObject<EntityType<SlimeGolemEntity>> SLIME_GOLEM = ENTITY_REGISTRY.register("slime_golem", () -> EntityType.Builder.of(SlimeGolemEntity::new, MobCategory.MISC).sized(0.7F, 1.8F).build("slime_golem"));
        public static final RegistryObject<EntityType<SlimeballEntity>> SLIMEBALL_ENTITY = ENTITY_REGISTRY.register("slimeball_entity", () -> EntityType.Builder.<SlimeballEntity>of(SlimeballEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).setUpdateInterval(3).setTrackingRange(16).setShouldReceiveVelocityUpdates(true).build("slimeball_entity"));
    }

    public static void init(IEventBus bus) {
        Blocks.BLOCK_REGISTRY.register(bus);
        Items.ITEM_REGISTRY.register(bus);
        Entities.ENTITY_REGISTRY.register(bus);
    }

    //subscribes to the register event for entities
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterRenderers event) {
        //registers the slime golem model
        event.registerEntityRenderer(Entities.SLIME_GOLEM.get(), SlimeGolemRenderer::new);
        event.registerEntityRenderer(Entities.SLIMEBALL_ENTITY.get(), ThrownItemRenderer::new);
    }

    //subscribes to the register event for entities
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //registers the slime golem layers
        event.registerLayerDefinition(SlimeGolemRenderer.MODEL_RES, SlimeGolemModel::createBodyLayer);
    }
}