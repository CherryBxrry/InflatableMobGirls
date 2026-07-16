package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypeTags;
import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.DamageSourcePredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.TagPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.advancements.predicates.entity.PlayerPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.KilledTrigger;
import net.minecraft.advancements.triggers.TameAnimalTrigger;
import net.minecraft.advancements.triggers.UsingItemTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

@SuppressWarnings({"deprecated", "removal"})
public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                registries,
                List.of(
                        new ModAdventureAdvancements(),
                        new ModEndAdvancements(),
                        new ModHusbandryAdvancements(),
                        new ModNetherAdvancements(),
                        new ModStoryAdvancements()
                )
        );
    }

    private static final class ModAdventureAdvancements implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.@NonNull Provider registries, @NonNull Consumer<AdvancementHolder> output) {
            HolderGetter<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
            HolderLookup<Item> items = registries.lookupOrThrow(Registries.ITEM);

            Advancement.Builder.advancement()
                    .parent(Identifier.withDefaultNamespace("adventure/spyglass_at_parrot"))
                    .display(
                            Items.SPYGLASS,
                            Component.translatable("advancements.inflatablemobgirls.adventure.spyglass_at_ghast_girl.title"),
                            Component.translatable("advancements.inflatablemobgirls.adventure.spyglass_at_ghast_girl.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false)
                    .addCriterion(
                            "spyglass_at_ghast_girl",
                            lookAtThroughItem(
                                    EntityPredicate.Builder.entity()
                                            .of(entityTypes, ModEntityTypes.GHAST_GIRL.get()),
                                    ItemPredicate.Builder.item().of(items, Items.SPYGLASS)
                            ))
                    .save(output, "adventure/spyglass_at_ghast_girl");
        }

        private static Criterion<UsingItemTrigger.TriggerInstance> lookAtThroughItem(final EntityPredicate.Builder lookingAt, final ItemPredicate.Builder with) {
            return UsingItemTrigger.TriggerInstance.lookingAt(
                    EntityPredicate.Builder.entity().player(
                            PlayerPredicate.Builder.player().setLookingAt(lookingAt).build()
                    ),
                    with
            );
        }
    }

    private static final class ModHusbandryAdvancements implements AdvancementSubProvider {
        public static final List<EntityType<?>> MOB_GIRLS;

        @Override
        public void generate(HolderLookup.@NonNull Provider registries, @NonNull Consumer<AdvancementHolder> output) {
            HolderGetter<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);

            AdvancementHolder tameAMobGirl = Advancement.Builder.advancement()
                    .parent(Identifier.withDefaultNamespace("husbandry/root"))
                    .display(
                            ModItems.CREEPER_GIRL_SPAWN_EGG.get(),
                            Component.translatable("advancements.inflatablemobgirls.husbandry.tame_a_mob_girl.title"),
                            Component.translatable("advancements.inflatablemobgirls.husbandry.tame_a_mob_girl.description"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion(
                            "tamed_mob_girl",
                            TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, ModEntityTypeTags.INFLATABLE_MOB_GIRL)))
                    )
                    .save(output, "husbandry/tame_a_mob_girl");

            createTameAllMobGirlsAdvancement(tameAMobGirl, output, entityTypes, MOB_GIRLS.stream());
        }

        public static AdvancementHolder createTameAllMobGirlsAdvancement(AdvancementHolder parent, Consumer<AdvancementHolder> output, HolderGetter<EntityType<?>> entityTypes, Stream<EntityType<?>> mobGirl) {
            return addMobGirl(Advancement.Builder.advancement(), mobGirl, entityTypes)
                    .parent(parent)
                    .display(
                            ModItems.SOUL_CHOCOLATE.get(),
                            Component.translatable("advancements.inflatablemobgirls.husbandry.tame_all_mob_girls.title"),
                            Component.translatable("advancements.inflatablemobgirls.husbandry.tame_all_mob_girls.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .save(output, "husbandry/tame_all_mob_girls");
        }

        private static Advancement.Builder addMobGirl(Advancement.Builder advancement, Stream<EntityType<?>> mobGirls, HolderGetter<EntityType<?>> entityTypes) {
            mobGirls.forEach((mobGirl) -> advancement.addCriterion(EntityType.getKey(mobGirl).toString(), TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(entityTypes, mobGirl))));
            return advancement;
        }

        static {
            MOB_GIRLS = List.of(
                    ModEntityTypes.CREEPER_GIRL.get(),
                    ModEntityTypes.GHAST_GIRL.get()
            );
        }
    }

    private static final class ModEndAdvancements implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.@NonNull Provider registries, @NonNull Consumer<AdvancementHolder> output) {

        }
    }

    private static final class ModNetherAdvancements implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.@NonNull Provider registries, @NonNull Consumer<AdvancementHolder> output) {
            HolderGetter<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);

            AdvancementHolder ohTheHumanity = Advancement.Builder.advancement()
                    .parent(Identifier.withDefaultNamespace("nether/root"))
                    .display(
                            Items.FIRE_CHARGE,
                            Component.translatable("advancements.inflatablemobgirls.nether.oh_the_humanity.title"),
                            Component.translatable("advancements.inflatablemobgirls.nether.oh_the_humanity.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true,
                            true,
                            false)
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .addCriterion(
                            "killed_ghast_girl",
                            KilledTrigger.TriggerInstance.playerKilledEntity(
                                    EntityPredicate.Builder.entity().of(entityTypes, ModEntityTypes.GHAST_GIRL.get()),
                                    DamageSourcePredicate.Builder.damageType()
                                            .tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))
                                            .direct(EntityPredicate.Builder.entity().of(entityTypes, EntityTypes.FIREBALL))))
                    .save(output, "nether/oh_the_humanity");
        }
    }

    private static final class ModStoryAdvancements implements AdvancementSubProvider {
        @Override
        public void generate(HolderLookup.@NonNull Provider registries, @NonNull Consumer<AdvancementHolder> output) {

        }
    }
}
