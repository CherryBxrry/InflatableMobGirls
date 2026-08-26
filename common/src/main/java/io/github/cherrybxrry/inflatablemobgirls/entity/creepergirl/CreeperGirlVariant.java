package io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.cherrybxrry.inflatablemobgirls.init.ModRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import org.jspecify.annotations.NonNull;

import java.util.List;

public record CreeperGirlVariant(AssetInfo adultInfo, AssetInfo babyInfo,
                                 SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {
    public static final Codec<CreeperGirlVariant> DIRECT_CODEC = RecordCodecBuilder.create((i) -> i.group(
                    AssetInfo.CODEC.fieldOf("assets").forGetter(CreeperGirlVariant::adultInfo),
                    AssetInfo.CODEC.fieldOf("baby_assets").forGetter(CreeperGirlVariant::babyInfo),
                    SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(CreeperGirlVariant::spawnConditions))
            .apply(i, CreeperGirlVariant::new)
    );
    public static final Codec<CreeperGirlVariant> NETWORK_CODEC = RecordCodecBuilder.create((i) -> i.group(
                    AssetInfo.CODEC.fieldOf("assets").forGetter(CreeperGirlVariant::adultInfo),
                    AssetInfo.CODEC.fieldOf("baby_assets").forGetter(CreeperGirlVariant::babyInfo))
            .apply(i, CreeperGirlVariant::new)
    );
    public static final Codec<Holder<CreeperGirlVariant>> CODEC = RegistryFixedCodec.create(ModRegistries.CREEPER_GIRL_VARIANT);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<CreeperGirlVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(ModRegistries.CREEPER_GIRL_VARIANT);

    private CreeperGirlVariant(AssetInfo adultInfo, AssetInfo babyInfo) {
        this(adultInfo, babyInfo, SpawnPrioritySelectors.EMPTY);
    }

    @Override
    public @NonNull List<Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }

    public record AssetInfo(ClientAsset.ResourceTexture normal, ClientAsset.ResourceTexture charged) {
        public static final Codec<AssetInfo> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                        ClientAsset.ResourceTexture.CODEC.fieldOf("normal").forGetter(AssetInfo::normal),
                        ClientAsset.ResourceTexture.CODEC.fieldOf("charged").forGetter(AssetInfo::charged)).apply(instance, AssetInfo::new)
        );
    }
}
