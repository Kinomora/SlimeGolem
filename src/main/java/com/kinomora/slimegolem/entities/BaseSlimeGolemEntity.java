package com.kinomora.slimegolem.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public class BaseSlimeGolemEntity extends GolemEntity {
    protected BaseSlimeGolemEntity(EntityType<? extends GolemEntity> type, World worldIn) {
        super(type, worldIn);
    }
}
