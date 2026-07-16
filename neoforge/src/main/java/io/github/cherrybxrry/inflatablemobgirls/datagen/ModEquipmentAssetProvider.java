package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.enums.client.ModEquipmentLayerTypes;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.function.BiConsumer;

public class ModEquipmentAssetProvider extends EquipmentAssetProvider {
    public ModEquipmentAssetProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(@NonNull BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        for(Map.Entry<DyeColor, ResourceKey<EquipmentAsset>> entry : EquipmentAssets.HARNESSES.entrySet()) {
            DyeColor color = entry.getKey();
            ResourceKey<EquipmentAsset> id = entry.getValue();
            output.accept(id, EquipmentClientInfo.builder()
                    .addLayers(EquipmentClientInfo.LayerType.HAPPY_GHAST_BODY, EquipmentClientInfo.Layer.onlyIfDyed(Identifier.withDefaultNamespace(color.getSerializedName() + "_harness"), false))
                    .addLayers(ModEquipmentLayerTypes.GHAST_GIRL_BODY, new EquipmentClientInfo.Layer[]{EquipmentClientInfo.Layer.onlyIfDyed(Constants.id(color.getSerializedName() + "_harness"), false)})
                    .build()
            );
        }
    }
}
