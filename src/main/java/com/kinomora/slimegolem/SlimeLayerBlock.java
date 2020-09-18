package com.kinomora.slimegolem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SlimeGolem.ID)
public class SlimeLayerBlock extends Block {

    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS_1_8;
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{VoxelShapes.empty(), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public SlimeLayerBlock() {
        //set the slime layer properties
        //materials match slime blocks (clay, grass, etc)
        super(Properties.create(Material.MISCELLANEOUS, MaterialColor.GRASS).slipperiness(0.8f).sound(SoundType.SLIME).notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(LAYERS, Integer.valueOf(1)));
    }

    @Override
    public Item asItem() {
        return Items.SLIME_BALL;
    }

    @SubscribeEvent
    public static void placeSlimeLayer(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote) {
            ItemStack item = event.getItemStack();
            BlockState state = event.getWorld().getBlockState(event.getPos());

            //checking if you're holding a slime ball
            if (item.getItem().equals(Items.SLIME_BALL)) {
                //and that the block you clicked on is a slime layer

                if (state.getBlock().equals(RegistryHandler.SLIME_LAYER)) {
                    int layers = state.get(SlimeLayerBlock.LAYERS);
                    //Lets you turn a full 8-layer stack of slime layers into a full slime block
                    if (layers == 8) {
                        event.getWorld().setBlockState(event.getPos(), Blocks.SLIME_BLOCK.getDefaultState(), 3);
                        if (!event.getPlayer().isCreative()) {
                            item.shrink(1);
                        }
                        event.setResult(Event.Result.ALLOW);
                        return;
                    }
                    //lets you add slime balls to slime layers to increase their height
                    else if (layers < 8) {
                        event.getWorld().setBlockState(event.getPos(), state.with(SlimeLayerBlock.LAYERS, layers + 1), 3);
                        if (!event.getPlayer().isCreative()) {
                            item.shrink(1);
                        }
                        event.setResult(Event.Result.ALLOW);
                        return;
                    }
                }

                //determine the side of the block that you right clicked on
                Direction facing = event.getFace();
                if (state.isReplaceable(new BlockItemUseContext(new ItemUseContext(event.getPlayer(), event.getHand(), new BlockRayTraceResult(event.getPlayer().getLookVec(), facing.getOpposite(), event.getPos(), false))))) {
                    if (RegistryHandler.SLIME_LAYER.isValidPosition(state, event.getWorld(), event.getPos())) {
                        event.getWorld().setBlockState(event.getPos(), RegistryHandler.SLIME_LAYER.getDefaultState());
                        if (!event.getPlayer().isCreative()) {
                            item.shrink(1);
                        }
                    }
                }
                else if (facing.equals(Direction.UP)) {
                    BlockPos newPlacement = event.getPos().offset(facing);
                    if (event.getWorld().getBlockState(newPlacement).isReplaceable(new BlockItemUseContext(new ItemUseContext(event.getPlayer(), event.getHand(), new BlockRayTraceResult(event.getPlayer().getLookVec(), facing.getOpposite(), newPlacement, false))))) {
                        if (RegistryHandler.SLIME_LAYER.isValidPosition(state, event.getWorld(), newPlacement)) {
                            event.getWorld().setBlockState(newPlacement, RegistryHandler.SLIME_LAYER.getDefaultState());
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            ItemStack item = player.getHeldItem(handIn);

            //checking if you're holding a slime ball
            if (item.getItem().equals(Items.SLIME_BALL)) {
                //and that the block you clicked on is a slime layer

            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, worldIn, pos, newState, isMoving);
        //checks to make sure the new block is (not) the same as the old block (slime layers increase don't drop slimeballs)
        if (!worldIn.isRemote && state.getBlock() != newState.getBlock()) {
            //check to make sure the new state isn't a vanilla slime block (converting 8+1 layers into a slimeblock shouldn't drop slimeballs)
            if (!worldIn.isRemote && newState.getBlock() != Blocks.SLIME_BLOCK) {
                int layers = state.get(LAYERS);
                ItemStack item = new ItemStack(Items.SLIME_BALL, layers);
                ItemEntity entity = new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, item);
                worldIn.addEntity(entity);
            }
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);

    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        switch (type) {
            case LAND:
                return state.get(LAYERS) < 5;
            case WATER:
                return false;
            case AIR:
                return false;
            default:
                return false;
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(LAYERS)];
    }

    public boolean isTransparent(BlockState state) {
        return true;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.down());
        Block block = blockstate.getBlock();
        if (block != Blocks.ICE && block != Blocks.PACKED_ICE && block != Blocks.BARRIER) {
            if (block != Blocks.HONEY_BLOCK && block != Blocks.SOUL_SAND) {
                return Block.doesSideFillSquare(blockstate.getCollisionShape(worldIn, pos.down()), Direction.UP) || block == this && blockstate.get(LAYERS) == 8;
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
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSuppressingBounce()) {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        }
        else {
            entityIn.onLivingFall(fallDistance, 0.0F);
        }

    }

    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    public void onLanded(IBlockReader worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.onLanded(worldIn, entityIn);
        }
        else {
            this.func_226946_a_(entityIn);
        }

    }

    private void func_226946_a_(Entity p_226946_1_) {
        Vec3d vec3d = p_226946_1_.getMotion();
        if (vec3d.y < 0.0D) {
            double d0 = p_226946_1_ instanceof LivingEntity ? 1.0D : 0.8D;
            p_226946_1_.setMotion(vec3d.x, -vec3d.y * d0, vec3d.z);
        }

    }

    /**
     * Called when the given entity walks on this Block
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        double d0 = Math.abs(entityIn.getMotion().y);
        if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            entityIn.setMotion(entityIn.getMotion().mul(d1, 1.0D, d1));
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
