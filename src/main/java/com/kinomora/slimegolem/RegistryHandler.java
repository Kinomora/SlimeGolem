package com.kinomora.slimegolem;

import com.kinomora.slimegolem.block.CarvedMelonBlock;
import com.kinomora.slimegolem.block.SlimeBlock;
import com.kinomora.slimegolem.block.SlimeLayerBlock;
import com.kinomora.slimegolem.entity.SlimeGolemEntity;
import com.kinomora.slimegolem.entity.SlimeballEntity;
import com.kinomora.slimegolem.render.SlimeGolemRenderer;
import com.kinomora.slimegolem.render.model.SlimeGolemModel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

//bus variable says this class is use for registering and subscribing to the event bus
@Mod.EventBusSubscriber(modid = SlimeGolems.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

    //an "object holder" makes it so that when a (new) object is created in the "registerblocks" method is created, it will be able to help find the object that is registered
    //do both of these for each block added, in order to easily reference the block you're adding
    @ObjectHolder(SlimeGolems.ID + ":slime_layer")
    public static Block SLIME_LAYER;

    @ObjectHolder(SlimeGolems.ID + ":slime_block")
    public static Block SLIME_BLOCK;

    @ObjectHolder(SlimeGolems.ID + ":carved_melon_block")
    public static Block CARVED_MELON_BLOCK;

    @ObjectHolder(SlimeGolems.ID + ":slime_golem")
    public static EntityType<SlimeGolemEntity> SLIME_GOLEM;

    @ObjectHolder(SlimeGolems.ID + ":slimeball_entity")
    public static EntityType<SlimeballEntity> SLIMEBALL_ENTITY;

    //subscribes to the register event for blocks
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //registers the blocks
        event.getRegistry().register(new SlimeLayerBlock().setRegistryName(SlimeGolems.createRes("slime_layer")));
        event.getRegistry().register(new SlimeBlock().setRegistryName(SlimeGolems.createRes("slime_block")));
        event.getRegistry().register(new CarvedMelonBlock().setRegistryName(SlimeGolems.createRes("carved_melon_block")));
    }

    //subscribes to the register event for items
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //registers the items
        //event.getRegistry().register(new BlockItem(SLIME_LAYER, new Item.Properties()).setRegistryName(SlimeGolems.createRes("slime_layer")));
        event.getRegistry().register(new BlockItem(SLIME_BLOCK, new Item.Properties()).setRegistryName(SlimeGolems.createRes("slime_block")));
        event.getRegistry().register(new BlockItem(CARVED_MELON_BLOCK, new Item.Properties()).setRegistryName(SlimeGolems.createRes("carved_melon_block")));

        //Register Eggs
        event.getRegistry().register(new ForgeSpawnEggItem(()->SLIME_GOLEM, 0x2ed941, 0x8fed5c, new Item.Properties().tab(CreativeModeTab.TAB_MISC)).setRegistryName(SlimeGolems.createRes("slime_golem_spawn_egg")));
        //event.getRegistry().register(new ForgeSpawnEggItem(()->SLIMY_IRON_GOLEM, 0x2ed941, 0x8fed5c, new Item.Properties().tab(CreativeModeTab.TAB_MISC)).setRegistryName(SlimeGolems.createRes("slimy_iron_golem_spawn_egg")));
    }

    //subscribes to the register event for entities
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> reg = event.getRegistry();

        //Register the Slimegolem (snowman) entity
        reg.register(EntityType.Builder.of(SlimeGolemEntity::new, MobCategory.MISC).sized(0.7F, 1.8F).build("slime_golem").setRegistryName(SlimeGolems.createRes("slime_golem")));

        //Register the Slimeball (snowball) entity
        reg.register(EntityType.Builder.<SlimeballEntity>of(SlimeballEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).setUpdateInterval(3).setTrackingRange(16).setShouldReceiveVelocityUpdates(true).build("slimeball_entity").setRegistryName(SlimeGolems.createRes("slimeball_entity")));
    }

    //subscribes to the register event for entities
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterRenderers event) {
        //registers the slime golem model
        event.registerEntityRenderer(SLIME_GOLEM, SlimeGolemRenderer::new);
        event.registerEntityRenderer(SLIMEBALL_ENTITY, ThrownItemRenderer::new);
    }

    //subscribes to the register event for entities
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //registers the slime golem layers
        event.registerLayerDefinition(SlimeGolemRenderer.MODEL_RES, SlimeGolemModel::createBodyLayer);
    }
}