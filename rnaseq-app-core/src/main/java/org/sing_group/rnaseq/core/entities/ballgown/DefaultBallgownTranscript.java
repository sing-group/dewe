package org.sing_group.rnaseq.core.entities.ballgown;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscript;

/**
 * The default {@code BallgownTranscript} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownTranscript
 */
public class DefaultBallgownTranscript implements BallgownTranscript {
	private String geneIds;
	private String geneNames;
	private String transcriptNames;
	private int transcriptId;
	private double foldChange;
	private double pVal;
	private double qVal;

	/**
	 * Creates a new {@code DefaultBallgownTranscript} instance with the
	 * specified initial values.
	 *
	 * @param geneIds the gene ids
	 * @param geneNames the gene names
	 * @param transcriptNames the transcript names
	 * @param transcriptId the transcript id
	 * @param foldChange the fold change associated to this gene
	 * @param pVal the p-value associated to this gene
	 * @param qVal the q-value associated to this gene
	 */
	public DefaultBallgownTranscript(String geneIds, String geneNames,
		String transcriptNames, int transcriptId, double foldChange,
		double pVal, double qVal) {
		this.geneIds = geneIds;
		this.geneNames = geneNames;
		this.transcriptNames = transcriptNames;
		this.transcriptId = transcriptId;
		this.foldChange = foldChange;
		this.pVal = pVal;
		this.qVal = qVal;
	}

	@Override
	public String getGeneIds() {
		return this.geneIds;
	}

	@Override
	public String getGeneNames() {
		return this.geneNames;
	}

	@Override
	public String getTranscriptNames() {
		return this.transcriptNames;
	}

	@Override
	public int getTranscriptId() {
		return this.transcriptId;
	}

	@Override
	public double getFoldChange() {
		return this.foldChange;
	}

	@Override
	public double getPval() {
		return this.pVal;
	}

	@Override
	public double getQval() {
		return this.qVal;
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof BallgownTranscript)) {
			return false;
		}
		BallgownTranscript that = (BallgownTranscript) aThat;

		return this.geneIds.equals(that.getGeneIds())
			&& this.geneNames.equals(that.getGeneNames())
			&& this.transcriptNames.equals(that.getTranscriptNames())
			&& this.transcriptId == that.getTranscriptId()
			&& this.foldChange == that.getFoldChange()
			&& this.pVal == that.getPval()
			&& this.qVal == that.getQval();
	}
}
