package org.sing_group.rnaseq.api.environment.binaries;

public interface SamtoolsBinaries extends Binaries {
	public final static String SAMTOOLS_PREFIX = "samtools.";
	public final static String BASE_DIRECTORY_PROP = SAMTOOLS_PREFIX + "binDir";

	public abstract String getSamToBam();
}
