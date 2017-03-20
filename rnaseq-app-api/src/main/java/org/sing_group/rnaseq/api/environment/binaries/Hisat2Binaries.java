package org.sing_group.rnaseq.api.environment.binaries;

public interface Hisat2Binaries extends Binaries {
	public final static String HISAT2_PREFIX = "hisat2.";
	public final static String BASE_DIRECTORY_PROP = HISAT2_PREFIX + "binDir";

	public abstract String getAlignReads();
}
