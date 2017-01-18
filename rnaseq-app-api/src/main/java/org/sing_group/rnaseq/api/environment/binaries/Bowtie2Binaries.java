package org.sing_group.rnaseq.api.environment.binaries;

public interface Bowtie2Binaries extends Binaries {
	public final static String BOWTIE2_PREFIX = "bowtie2.";
	public final static String BASE_DIRECTORY_PROP = BOWTIE2_PREFIX + "binDir";

	public abstract String getBuildIndex();

	public abstract String getAlignReads();
}
