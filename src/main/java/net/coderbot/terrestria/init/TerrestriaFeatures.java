package net.coderbot.terrestria.init;

import io.github.terraformersmc.terraform.block.SmallLogBlock;
import io.github.terraformersmc.terraform.feature.CattailFeature;
import net.coderbot.terrestria.Terrestria;
import net.coderbot.terrestria.feature.*;
import net.coderbot.terrestria.feature.volcano.VolcanoGenerator;
import net.coderbot.terrestria.feature.volcano.VolcanoStructureFeature;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OakTreeFeature;
import net.minecraft.world.gen.feature.SeagrassFeatureConfig;

public class TerrestriaFeatures {
	public static CypressTreeFeature CYPRESS_TREE;
	public static CypressTreeFeature SMALL_BALD_CYPRESS_TREE;
	public static OakTreeFeature TALLER_BIRCH_TREE;
	public static SakuraTreeFeature SAKURA_TREE;
	public static JapaneseMapleTreeFeature JAPANESE_MAPLE_TREE;
	public static ShrubFeature JAPANESE_MAPLE_SHRUB;
	public static MegaCanopyTreeFeature RAINBOW_EUCALYPTUS_TREE;
	public static MegaCanopyTreeFeature BALD_CYPRESS_TREE;
	public static CattailFeature CATTAIL;
	public static PalmTreeFeature PALM_TREE;

	public static VolcanoStructureFeature VOLCANO_STRUCTURE;
	public static StructurePieceType VOLCANO_PIECE;

	public static void init() {
		CYPRESS_TREE = register("cypress_tree",
				new CypressTreeFeature(DefaultFeatureConfig::deserialize, false, TerrestriaBlocks.CYPRESS.getBasicDefinition())
		);

		SMALL_BALD_CYPRESS_TREE = register("small_bald_cypress_tree",
				new CypressTreeFeature(DefaultFeatureConfig::deserialize, false, TerrestriaBlocks.BALD_CYPRESS.getBasicDefinition())
		);

		TALLER_BIRCH_TREE = register("taller_birch_tree",
				new OakTreeFeature(DefaultFeatureConfig::deserialize, false, 8, Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState(), false)
		);

		SAKURA_TREE = register("sakura_tree",
				new SakuraTreeFeature(DefaultFeatureConfig::deserialize, false, TerrestriaBlocks.SAKURA.getBasicDefinition().toSakura (
						TerrestriaBlocks.SAKURA.log.getDefaultState().with(SmallLogBlock.HAS_LEAVES, true),
						TerrestriaBlocks.SAKURA_LEAF_PILE.getDefaultState()
				))
		);

		JAPANESE_MAPLE_TREE = register("japanese_maple_tree",
				new JapaneseMapleTreeFeature(DefaultFeatureConfig::deserialize, false, TerrestriaBlocks.JAPANESE_MAPLE.getBasicDefinition())
		);

		TreeDefinition.Basic shrubDefinition = new TreeDefinition.Basic (
				TerrestriaBlocks.JAPANESE_MAPLE.log.getDefaultState(),
				TerrestriaBlocks.JAPANESE_MAPLE_SHRUB_LEAVES.getDefaultState()
		);

		JAPANESE_MAPLE_SHRUB = register("japanese_maple_shrub",
				new ShrubFeature(DefaultFeatureConfig::deserialize, false, shrubDefinition)
		);

		RAINBOW_EUCALYPTUS_TREE = register("rainbow_eucalyptus_tree",
				new MegaCanopyTreeFeature(DefaultFeatureConfig::deserialize, false, TerrestriaBlocks.RAINBOW_EUCALYPTUS.getBasicDefinition().toMega (
						TerrestriaBlocks.RAINBOW_EUCALYPTUS_QUARTER_LOG.getDefaultState(),
						TerrestriaBlocks.RAINBOW_EUCALYPTUS.wood.getDefaultState()
				))
		);

		BALD_CYPRESS_TREE = register("bald_cypress_tree",
				new MegaCanopyTreeFeature(DefaultFeatureConfig::deserialize, false, TerrestriaBlocks.BALD_CYPRESS.getBasicDefinition().toMega (
						TerrestriaBlocks.BALD_CYPRESS_QUARTER_LOG.getDefaultState(),
						TerrestriaBlocks.BALD_CYPRESS.wood.getDefaultState()
				))
		);

		CATTAIL = register("cattail",
				new CattailFeature(SeagrassFeatureConfig::deserialize, TerrestriaBlocks.CATTAIL, TerrestriaBlocks.TALL_CATTAIL)
		);

		// TODO: palm wood
		TreeDefinition.Basic palmDefinition = new TreeDefinition.Basic (
				Blocks.JUNGLE_LOG.getDefaultState(),
				Blocks.JUNGLE_LEAVES.getDefaultState()
		);

		PALM_TREE = register("palm_tree",
				new PalmTreeFeature(DefaultFeatureConfig::deserialize, false, palmDefinition.withBark(Blocks.JUNGLE_WOOD.getDefaultState()))
		);

		VOLCANO_STRUCTURE = Registry.register(Registry.STRUCTURE_FEATURE, new Identifier(Terrestria.MOD_ID, "volcano"),
				new VolcanoStructureFeature(DefaultFeatureConfig::deserialize)
		);

		Feature.STRUCTURES.put("volcano", VOLCANO_STRUCTURE);

		VOLCANO_PIECE = Registry.register(Registry.STRUCTURE_PIECE, new Identifier(Terrestria.MOD_ID, "volcano"), VolcanoGenerator::new);
	}

	public static <T extends Feature> T register(String name, T feature) {
		return Registry.register(Registry.FEATURE, new Identifier(Terrestria.MOD_ID, name), feature);
	}
}