package io.github.cherrybxrry.inflatablemobgirls.mixin;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EquipmentClientInfo.LayerType.class)
enum EquipmentClientInfoLayerTypeMixin {
    inflatablemobgirls_GHAST_GIRL_BODY("inflatablemobgirls/ghast_girl_body");


    @Shadow
    EquipmentClientInfoLayerTypeMixin(String id) {
    }
}
