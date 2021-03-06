package com.terraformersmc.terrestria.feature.tree.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.impl.Shapes;
import com.terraformersmc.terraform.shapes.impl.layer.pathfinder.SubtractLayer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.TranslateLayer;
import com.terraformersmc.terrestria.feature.tree.foliageplacers.templates.SmallFoliagePlacer;
import com.terraformersmc.terrestria.init.TerrestriaFoliagePlacerTypes;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class SmallCanopyFoliagePlacer extends SmallFoliagePlacer {

	public static final Codec<SmallCanopyFoliagePlacer> CODEC = RecordCodecBuilder.create(smallCanopyFoliagePlacerInstance ->
			fillFoliagePlacerFields(smallCanopyFoliagePlacerInstance).apply(smallCanopyFoliagePlacerInstance, SmallCanopyFoliagePlacer::new));

	public SmallCanopyFoliagePlacer(UniformIntDistribution radius, UniformIntDistribution offset) {
		super(radius, offset);
	}

	@Override
	protected FoliagePlacerType<?> getType() {
		return TerrestriaFoliagePlacerTypes.SMALL_CANOPY;
	}

	@Override
	protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int diameter, Set<BlockPos> leaves, int i, BlockBox blockBox) {

		diameter = treeNode.getFoliageRadius() * 2;
		BlockPos pos = treeNode.getCenter();

		Shapes.hemiEllipsoid(diameter * 1.1, diameter * 1.1, diameter * 1.8)
				.applyLayer(new SubtractLayer(Shapes.hemiEllipsoid(diameter - 2, diameter - 2, diameter - 1)))
				.applyLayer(TranslateLayer.of(Position.of(pos.down(2))))
				.stream()
				.forEach((position) -> {
					//On the bottom layer only place 50% of the blocks
					if (position.getY() != (pos.getY() - 1) || random.nextBoolean()) {
						tryPlaceLeaves(world, position.toBlockPos(), random, leaves, config);
					}
				});

	}

	@Override
	public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
		return 0;
	}

	@Override
	protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
		return false;
	}
}
