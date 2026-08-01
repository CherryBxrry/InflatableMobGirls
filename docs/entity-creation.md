# Adding a New `InflatableMobGirl` Entity

This guide explains how to add a new entity that extends `InflatableMobGirl` to this mod. It assumes you are familiar with the project layout and the existing `CreeperGirl` implementation (used as the canonical example). Variants are optional — the guide shows both a minimal entity and the optional steps to add variant support (textures, datapack registry, and network codecs).

## 1. Create the entity class

**Location:** `io.github.cherrybxrry.inflatablemobgirls.entities.<yourentity>`

**Base:** extend `InflatableMobGirl`.

### Essential overrides

- Constructor: `public YourGirl(EntityType<YourGirl> type, Level level)`
- `public static AttributeSupplier.Builder createAttributes()` — build attributes similar to `CreeperGirl.createAttributes()`.
- `protected void registerGoals()` — register AI goals.
- `protected void defineSynchedData(SynchedEntityData.Builder entityData)`
- `protected void addAdditionalSaveData(ValueOutput output)`
- `protected void readAdditionalSaveData(ValueInput input)`
- `protected void setupAnimationStates()`
- `protected AnimationState getAttackAnimation()`
- `protected EntityDimensions getStandingDimensions()`
- `protected EntityDimensions getSittingDimensions()`
- `protected boolean isTameItem(ItemStack)`
- `protected boolean isInflateItem(ItemStack)`
- `public int getMaxStage()`
- `public int getAttackDelay()`
- `protected int getAttackLength()`

> Tip: Copy `CreeperGirl` and remove or adapt features you don't need.

## 2. Register the EntityType

**File:** `init/ModEntityTypes.java`

```java
    public static final RegistryHandle<EntityType<YourGirl>> YOUR_GIRL =
        Services.REGISTRY.registerEntityType(
            "your_girl",
            EntityType.Builder.of(YourGirl::new, MobCategory.CREATURE)
        );
```

## 3. Register attributes

**File:** `init/ModEntityAttributes.java`

```java
    Services.ATTRIBUTES.registerEntityAttributes(
        ModEntityTypes.YOUR_GIRL,
        YourGirl::createAttributes
    );
```

## 4. Spawn placement and spawn settings

**File:** `init/ModEntitySpawns.java`

### Spawn placement

```java
    Services.SPAWN_PLACEMENTS.registerSpawnPlacement(
        ModEntityTypes.YOUR_GIRL,
        SpawnPlacementTypes.ON_GROUND,
        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
        YourGirl::canSpawnLight
    );
```

### Spawn settings

```java
    public static final SpawnSettings YOUR_GIRL = 
        new SpawnSettings(
                8, // Spawn weights
                1, // Min group size
                2  // Max group size
        );
```

### Worldgen

- Fabric: `FabricWorldGen.load()` → `BiomeModifications.addSpawn(...)`
- NeoForge: `ModWorldGenProvider.bootstrapBiomeModifiers(...)`

## 5. Register renderer and model layers



### Model layers

**File:** `init/Client/ModModelLayers.java`

```java
    ServicesClient.CLIENT_REGISTRY.registerModelLayer(
        YourAdultModel.LAYER_LOCATION,
        YourAdultModel::createBodyLayer
    );
    
    ServicesClient.CLIENT_REGISTRY.registerModelLayer(
        YourBabyModel.LAYER_LOCATION,
        YourBabyModel::createBodyLayer
    );
```

### Renderer

**File:** `init/Client/ModEntityRenderers.java`

```java
    ServicesClient.CLIENT_REGISTRY.registerEntityRenderer(
        ModEntityTypes.YOUR_GIRL.get(),
        YourGirlRenderer::new
    );
```

### Renderer implementation

Extend:

```java
    AgeableMobRenderer<YourGirl, YourRenderState, YourModel>
```

Implement:

- `createRenderState()`
- `getTextureLocation(...)`
- `extractRenderState(...)`

## 6. Models and animations

### Models

**Location:** `io.github.cherrybxrry.inflatablemobgirls.client.model.entity`

Create:

- `YourGirlModel`

Optionally:

- `AdultYourGirlModel`
- `BabyYourGirlModel`

### Animations

**Location:** `io.github.cherrybxrry.inflatablemobgirls.client.animation.definitions`

Add:

- idle
- inflate
- sit
- attack

### Render state

**Location:** `io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state`

Create `YourGirlRenderState` similar to `CreeperGirlRenderState`.

## 7. Sounds, loot, and particles

### Sounds

**File:** `init/Client/ModSounds.java`

```java
    Services.REGISTRY.registerSoundEvent("your_girl_sound");
```

### Loot

**File:** `datagen/ModLootProvider.java`

```java
    add(ModEntityTypes.YOUR_GIRL.get(), LootTable.lootTable()...);
```

## 8. Variant support (optional)

### A. Create a Variant record

**Location:** `io.github.cherrybxrry.inflatablemobgirls.entities.<yourentity>`

```java
    public record YourVariant(
        AssetInfo adultInfo,
        AssetInfo babyInfo,
        SpawnPrioritySelectors spawnConditions
    ) {
        public static final Codec<YourVariant> DIRECT_CODEC = ...;
        public static final Codec<Holder<YourVariant>> CODEC = ...;
        public static final StreamCodec<RegistryFriendlyByteBuf,
            Holder<YourVariant>> STREAM_CODEC = ...;
    }
```

### B. Create a Variants class

**Location:** `io.github.cherrybxrry.inflatablemobgirls.entities.<yourentity>`

```java
    public class YourVariants {
        public static final ResourceKey<YourVariant> EXAMPLE = createKey("example");
        public static final ResourceKey<YourVariant> DEFAULT = EXAMPLE;
    
        private static ResourceKey<YourVariant> createKey(String name) {
            return ResourceKey.create(ModRegistries.YOUR_GIRL_VARIANT, Constants.id(name));
        }
            
        ...
    
        public static void bootstrap(BootstrapContext<CreeperGirlVariant> context) {
            register(context, EXAMPLE, "your_girl", ...);
        }
    }
```

### C. Registry key

**File:** `init/ModRegistries.java`

```java
    public static final ResourceKey<Registry<YourVariant>> YOUR_VARIANT =
        createRegistryKey("your_variant");
```

### D. Datapack registry bootstrap

**File:** `init/ModDatapackRegistries.java`

```java
    Services.DATAPACK_REGISTRIES.registerSynced(
        ModRegistries.YOUR_VARIANT,
        YourVariant.DIRECT_CODEC,
        YourVariant.NETWORK_CODEC
    );
```

### E. Data component

**File:** `init/ModDataComponents.java`

```java
    public static final RegistryHandle<DataComponentType<Holder<YourVariant>>>
        YOUR_VARIANT_COMPONENT =
            Services.REGISTRY.registerDataComponentType(
                "your_girl/variant",
                b -> b.persistent(YourVariant.CODEC)
                      .networkSynchronized(YourVariant.STREAM_CODEC)
            );
```

### Entity data serializer

**File:** `init/ModEntityDataSerializers.java`

```java
    public static final RegistryHandle<EntityDataSerializer<Holder<YourVariant>>> 
        YOUR_VARIANT_SERIALIZER =
        Services.REGISTRY.registerEntityDataSerializer(
            "your_girl_variant",
            YourVariant.STREAM_CODEC
        );
```

### F. Bootstrap variants

**File:** `init/ModEntityDataSerializers.java`

```java
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(ModRegistries.YOUR_GIRL_VARIANT, YourGirlVariants::bootstrap);
```

## Minimal Checklist

- [ ] Create `YourGirl`
- [ ] Register `EntityType`
- [ ] Register attributes
- [ ] Add spawn placement
- [ ] Add spawn settings
- [ ] Register renderer
- [ ] Register model layers
- [ ] Implement models
- [ ] Implement animations
- [ ] Add loot tables
- [ ] Add sounds
- [ ] Optional variant support
- [ ] Wire into platform initialization

## Example Snippets

### EntityType

```java
    public static final RegistryHandle<EntityType<YourGirl>> YOUR_GIRL =
        Services.REGISTRY.registerEntityType(
            "your_girl",
            EntityType.Builder.of(YourGirl::new, MobCategory.CREATURE)
        );
```

### Attributes

```java
    Services.ATTRIBUTES.registerEntityAttributes(
        ModEntityTypes.YOUR_GIRL,
        YourGirl::createAttributes
    );
```

### Model Layer

```java
    ServicesClient.CLIENT_REGISTRY.registerModelLayer(
        YourGirlModel.LAYER_LOCATION,
        YourGirlModel::createBodyLayer
    );
```

### Renderer

```java
    ServicesClient.CLIENT_REGISTRY.registerEntityRenderer(
        ModEntityTypes.YOUR_GIRL.get(),
        YourGirlRenderer::new
    );
```

## Notes and Best Practices

- Follow `CreeperGirl` as the canonical example.
- Keep network-synced data minimal.
- Test baby and adult dimensions carefully.
