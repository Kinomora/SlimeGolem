package com.kinomora.slimegolem.block;

import com.kinomora.slimegolem.RegistryHandler;
import com.kinomora.slimegolem.SlimeGolems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SlimeGolems.ID)
public class SlimeLayerBlock extends Block {

    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{Shapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public SlimeLayerBlock() {
        //set the slime layer properties
        //materials match slime blocks (misc, grass, etc)
        super(Properties.of(Material.DECORATION, MaterialColor.GRASS).friction(0.8f).sound(SoundType.SLIME_BLOCK).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, Integer.valueOf(1)));
    }

    @Override
    public Item asItem() {
        return Items.SLIME_BALL;
    }

    @SubscribeEvent
    public static void placeSlimeLayer(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isClientSide) {
            ItemStack item = event.getItemStack();
            BlockState state = event.getWorld().getBlockState(event.getPos());

            //checking if you're holding a slime ball
            if (item.getItem().equals(Items.SLIME_BALL)) {
                //and that the block you clicked on is a slime layer

                if (state.getBlock().equals(RegistryHandler.Blocks.SLIME_LAYER.get())) {
                    int layers = state.getValue(SlimeLayerBlock.LAYERS);
                    //Lets you turn a full 8-layer stack of slime layers into a full slime block
                    if (layers == 8) {
                        event.getWorld().setBlock(event.getPos(), Blocks.SLIME_BLOCK.defaultBlockState(), 3);
                        if (!event.getPlayer().isCreative()) {
                            item.shrink(1);
                        }
                        event.setResult(Event.Result.ALLOW);
                        return;
                    }
                    //lets you add slime balls to slime layers to increase their height
                    else if (layers < 8) {
                        event.getWorld().setBlock(event.getPos(), state.setValue(SlimeLayerBlock.LAYERS, layers + 1), 3);
                        if (!event.getPlayer().isCreative()) {
                            item.shrink(1);
                        }
                        event.setResult(Event.Result.ALLOW);
                        return;
                    }
                }

                //determine the side of the block that you right clicked on
                Direction facing = event.getFace();
                if (state.canBeReplaced(new BlockPlaceContext(new UseOnContext(event.getPlayer(), event.getHand(), new BlockHitResult(event.getPlayer().getLookAngle(), facing.getOpposite(), event.getPos(), false))))) {
                    if (RegistryHandler.Blocks.SLIME_LAYER.get().canSurvive(state, event.getWorld(), event.getPos())) {
                        event.getWorld().setBlockAndUpdate(event.getPos(), RegistryHandler.Blocks.SLIME_LAYER.get().defaultBlockState());
                        if (!event.getPlayer().isCreative()) {
                            item.shrink(1);
                        }
                    }
                }
                else if (facing.equals(Direction.UP)) {
                    BlockPos newPlacement = event.getPos().relative(facing);
                    if (event.getWorld().getBlockState(newPlacement).canBeReplaced(new BlockPlaceContext(new UseOnContext(event.getPlayer(), event.getHand(), new BlockHitResult(event.getPlayer().getLookAngle(), facing.getOpposite(), newPlacement, false))))) {
                        if (RegistryHandler.Blocks.SLIME_LAYER.get().canSurvive(state, event.getWorld(), newPlacement)) {
                            event.getWorld().setBlockAndUpdate(newPlacement, RegistryHandler.Blocks.SLIME_LAYER.get().defaultBlockState());
                            if (!event.getPlayer().isCreative()) {
                                item.shrink(1);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, worldIn, pos, newState, isMoving);
        //checks to make sure the new block is (not) the same as the old block (slime layers increase don't drop slimeballs)
        if (!worldIn.isClientSide && state.getBlock() != newState.getBlock()) {
            //check to make sure the new state isn't a vanilla slime block (converting 8+1 layers into a slimeblock shouldn't drop slimeballs)
            if (!worldIn.isClientSide && newState.getBlock() != Blocks.SLIME_BLOCK) {
                int layers = state.getValue(LAYERS);
                ItemStack item = new ItemStack(Items.SLIME_BALL, layers);
                ItemEntity entity = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, item);
                worldIn.addFreshEntity(entity);
            }
        }
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);

    }

    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        switch (type) {
            case LAND:
                return state.getValue(LAYERS) < 5;
            case WATER:
                return false;
            case AIR:
                return false;
            default:
                return false;
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(LAYERS)];
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.below());
        Block block = blockstate.getBlock();
        if (block != Blocks.ICE && block != Blocks.PACKED_ICE && block != Blocks.BARRIER) {
            if (block != Blocks.HONEY_BLOCK && block != Blocks.SOUL_SAND) {
                return Block.isFaceFull(blockstate.getCollisionShape(worldIn, pos.below()), Direction.UP) || block == this && blockstate.getValue(LAYERS) == 8;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    @Override
    public void fallOn(Level worldIn, BlockState blockState, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSuppressingBounce()) {
            super.fallOn(worldIn, blockState, pos, entityIn, fallDistance);
        }
        else {
            entityIn.causeFallDamage(fallDistance, 0.0F, DamageSource.FALL);
        }

    }

    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(worldIn, entityIn);
        } else {
            this.bounceEntity(entityIn);
        }

    }

    private void bounceEntity(Entity entity) {
        Vec3 vector3d = entity.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
        }

    }

    /**
     * Called when the given entity walks on this Block
     */
    public void stepOn(Level worldIn, BlockPos pos, BlockState blockState, Entity entityIn) {
        double d0 = Math.abs(entityIn.getDeltaMovement().y);
        if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(d1, 1.0D, d1));
        }

        super.stepOn(worldIn, pos, blockState, entityIn);
    }
}
