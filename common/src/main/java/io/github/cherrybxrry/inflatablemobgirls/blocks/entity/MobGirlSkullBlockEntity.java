package io.github.cherrybxrry.inflatablemobgirls.blocks.entity;

import io.github.cherrybxrry.inflatablemobgirls.blocks.MobGirlSkullBlock;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class MobGirlSkullBlockEntity extends BlockEntity {
    private static final String TAG_NOTE_BLOCK_SOUND = "note_block_sound";
    private static final String TAG_CUSTOM_NAME = "custom_name";
    private @Nullable Identifier noteBlockSound;
    private int animationTickCount;
    private boolean isAnimating;
    private @Nullable Component customName;

    public MobGirlSkullBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntityTypes.MOB_GIRL_SKULL.get(), worldPosition, blockState);
    }

    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.storeNullable(TAG_NOTE_BLOCK_SOUND, Identifier.CODEC, this.noteBlockSound);
        output.storeNullable(TAG_CUSTOM_NAME, ComponentSerialization.CODEC, this.customName);
    }

    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        this.noteBlockSound = input.read(TAG_NOTE_BLOCK_SOUND, Identifier.CODEC).orElse(null);
        this.customName = parseCustomNameSafe(input, TAG_CUSTOM_NAME);
    }

    public static void animation(Level level, BlockPos pos, BlockState state, MobGirlSkullBlockEntity entity) {
        if (state.hasProperty(MobGirlSkullBlock.POWERED) && state.getValue(MobGirlSkullBlock.POWERED)) {
            entity.isAnimating = true;
            ++entity.animationTickCount;
        } else {
            entity.isAnimating = false;
        }

    }

    public float getAnimation(float a) {
        return this.isAnimating ? (float)this.animationTickCount + a : (float)this.animationTickCount;
    }

    public @Nullable Identifier getNoteBlockSound() {
        return this.noteBlockSound;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return this.saveCustomOnly(registries);
    }

    protected void applyImplicitComponents(@NonNull DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.noteBlockSound = components.get(DataComponents.NOTE_BLOCK_SOUND);
        this.customName = components.get(DataComponents.CUSTOM_NAME);
    }

    protected void collectImplicitComponents(DataComponentMap.@NonNull Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.NOTE_BLOCK_SOUND, this.noteBlockSound);
        components.set(DataComponents.CUSTOM_NAME, this.customName);
    }

    public void removeComponentsFromTag(@NonNull ValueOutput output) {
        super.removeComponentsFromTag(output);
        output.discard(TAG_NOTE_BLOCK_SOUND);
        output.discard(TAG_CUSTOM_NAME);
    }
}
