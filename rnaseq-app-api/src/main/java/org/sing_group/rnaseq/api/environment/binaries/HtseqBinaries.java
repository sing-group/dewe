package org.sing_group.rnaseq.api.environment.binaries;

public interface HtseqBinaries extends Binaries {
	public final static String HTSEQ_PREFIX = "htseq.";
	public final static String BASE_DIRECTORY_PROP = HTSEQ_PREFIX + "binDir";

	public abstract String getHtseqCount();
}
