package com.kinomora.slimegolem;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

//bus variable says this class is use for registering and subscribing to the event bus
@Mod.EventBusSubscriber(modid = SlimeGolem.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

    //an "object holder" makes it so that when a (new) object is created in the "registerblocks" method is created, it will be able to help find the object that is registered
    //do both of these for each block added, in order to easily reference the block you're adding
    @ObjectHolder(SlimeGolem.ID + ":slime_layer")
    public static Block SLIME_LAYER;

    @ObjectHolder(SlimeGolem.ID + ":slime_block")
    public static Block SLIME_BLOCK;

    @ObjectHolder(SlimeGolem.ID + ":carved_melon_block")
    public static Block CARVED_MELON_BLOCK;

    @ObjectHolder(SlimeGolem.ID + ":slime_golem")
    public static EntityType<SlimeGolemEntity> SLIME_GOLEM;

    @ObjectHolder(SlimeGolem.ID + ":slimeball_entity")
    public static EntityType<SlimeballEntity> SLIMEBALL_ENTITY;

    //subscribes to the register event for blocks
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //registers the blocks
        event.getRegistry().register(new SlimeLayerBlock().setRegistryName(SlimeGolem.createRes("slime_layer")));
        event.getRegistry().register(new SlimeBlock().setRegistryName(SlimeGolem.createRes("slime_block")));
        event.getRegistry().register(new CarvedMelonBlock().setRegistryName(SlimeGolem.createRes("carved_melon_block")));
    }

    //subscribes to the register event for items
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //registers the items
        event.getRegistry().register(new BlockItem(SLIME_LAYER, new Item.Properties()).setRegistryName(SlimeGolem.createRes("slime_layer")));
        event.getRegistry().register(new BlockItem(SLIME_BLOCK, new Item.Properties()).setRegistryName(SlimeGolem.createRes("slime_block")));
        event.getRegistry().register(new BlockItem(CARVED_MELON_BLOCK, new Item.Properties()).setRegistryName(SlimeGolem.createRes("carved_melon_block")));
    }

    //subscribes to the register event for entities
    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> reg = event.getRegistry();

        //Register the Slimegolem (snowman) entity
        reg.register(EntityType.Builder.create(SlimeGolemEntity::new, EntityClassification.MISC).size(0.7F, 1.8F).build("slime_golem").setRegistryName(SlimeGolem.createRes("slime_golem")));

        //Register the Slimeball (snowball) entity
        reg.register(EntityType.Builder.<SlimeballEntity>create(SlimeballEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).setUpdateInterval(3).setTrackingRange(16).setShouldReceiveVelocityUpdates(true).build("slimeball_entity").setRegistryName(SlimeGolem.createRes("slimeball_entity")));
    }

    //subscribes to the register event for entities
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        //registers the slime golem model
        RenderingRegistry.registerEntityRenderingHandler(SLIME_GOLEM, SlimeGolemRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SLIMEBALL_ENTITY, renderManager -> new SpriteRenderer<>(renderManager, Minecraft.getInstance().getItemRenderer()));
    }
}