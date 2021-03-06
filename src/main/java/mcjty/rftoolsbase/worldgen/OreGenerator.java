package mcjty.rftoolsbase.worldgen;

import mcjty.rftoolsbase.modules.worldgen.WorldGenSetup;
import mcjty.rftoolsbase.setup.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class OreGenerator {

    private static final Predicate<BlockState> IS_NETHERACK = state -> state.getBlock() == Blocks.NETHERRACK;
    private static final Predicate<BlockState> IS_ENDSTONE = state -> state.getBlock() == Blocks.END_STONE;

    public static void init() {
        for (Biome biome : ForgeRegistries.BIOMES) {

            int overworldChances = Config.OVERWORLD_ORE_CHANCES.get();
            if (overworldChances > 0) {
                ConfiguredFeature<?> featureOverworld = Biome.createDecoratedFeature(Feature.ORE,
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, WorldGenSetup.DIMENSIONAL_SHARD_OVERWORLD.get().getDefaultState(),
                                Config.OVERWORLD_ORE_VEINSIZE.get()),
                        Placement.COUNT_RANGE, new CountRangeConfig(
                                overworldChances,
                                Config.OVERWORLD_ORE_MINY.get(),
                                0,
                                Config.OVERWORLD_ORE_MAXY.get() - Config.OVERWORLD_ORE_MINY.get()));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new DimensionCompositeFeature(featureOverworld, DimensionType.OVERWORLD));
            }


            int netherChances = Config.NETHER_ORE_CHANCES.get();
            if (netherChances > 0) {
                ConfiguredFeature<?> featureNether = Biome.createDecoratedFeature(Feature.ORE,
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, WorldGenSetup.DIMENSIONAL_SHARD_NETHER.get().getDefaultState(),
                                Config.NETHER_ORE_VEINSIZE.get()),
                        Placement.COUNT_RANGE, new CountRangeConfig(
                                netherChances,
                                Config.NETHER_ORE_MINY.get(),
                                0,
                                Config.NETHER_ORE_MAXY.get() - Config.NETHER_ORE_MINY.get()));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new DimensionCompositeFeature(featureNether, DimensionType.THE_NETHER));
            }
//            ConfiguredFeature<?> featureEnd = Biome.createDecoratedFeature(Feature.ORE,
//                    new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.DIMENSIONAL_SHARD_END.getDefaultState(), 8),
//                    Placement.COUNT_RANGE, placementConfig);
//            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new DimensionCompositeFeature(featureEnd, DimensionType.THE_END));
        }
    }
}
