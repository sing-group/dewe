package org.sing_group.rnaseq.core.operations.ballgown;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownGene;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownGenes;

/**
 * This class provides methods to manipulate {@code BallgownGenes} lists.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownGenesOperations {

	public enum GenesType {
		OVEREXPRESSED("Overexpressed"),
		UNDEREXPRESSED("Underexpressed");

		private String label;

		GenesType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return this.label;
		}
	}

	/**
	 * Returns a list containing the names of the most overexpressed or
	 * underexpressed genes in {@code genes}. The number of genes to be
	 * retrieved is specified by {@code genesCount}. Note that if this value is
	 * larger than the actual size of {@code genes}, then all genes will be
	 * returned.
	 *
	 * @param genes the list of {@code BallgownGenes}
	 * @param genesCount the number of genes to retrieve
	 * @param genesType the type of genes that must be retrieved
	 *
	 * @return a list containing the gene names
	 */
	public static List<String> getTopGeneNames(BallgownGenes genes,
		int genesCount, GenesType genesType
	) {
		return getGeneNames(
					getFirstNgenes(
						sortByFoldChange(
							excludeGenesWithoutName(genes), genesType),
							genesCount
						)
				);
	}

	private static Stream<BallgownGene> excludeGenesWithoutName(
		BallgownGenes genes
	) {
		return genes.stream().filter(g -> !g.getGeneName().equals("."));
	}

	private static BallgownGenes sortByFoldChange(
		Stream<BallgownGene> genes, GenesType genesType
	) {
		List<BallgownGene> sortedGenesList = genes
			.sorted(new Comparator<BallgownGene>() {

				@Override
				public int compare(BallgownGene o1, BallgownGene o2) {
					int compareTo = new Double(o1.getFoldChange())
						.compareTo(new Double(o2.getFoldChange()));
					return genesType.equals(GenesType.OVEREXPRESSED)
						? -compareTo : compareTo;
				}
			}).collect(Collectors.toList());

		return asGenesList(sortedGenesList);
	}

	private static BallgownGenes getFirstNgenes(BallgownGenes genes, int n) {
		return asGenesList(genes.subList(0, getGenesCount(genes, n)));
	}

	private static BallgownGenes asGenesList(List<BallgownGene> list) {
		return new DefaultBallgownGenes(list);
	}

	private static int getGenesCount(List<BallgownGene> sortedGenes,
		int genesCount) {
		return genesCount >= sortedGenes.size() ? sortedGenes.size() - 1
			: genesCount;
	}

	private static List<String> getGeneNames(BallgownGenes genes) {
		return genes.stream().map(g -> g.getGeneName())
			.collect(Collectors.toList());
	}
}
