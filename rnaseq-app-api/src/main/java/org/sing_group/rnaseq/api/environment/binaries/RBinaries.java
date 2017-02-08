package org.sing_group.rnaseq.api.environment.binaries;

public interface RBinaries extends Binaries {
	public final static String R_PREFIX = "r.";
	public final static String BASE_DIRECTORY_PROP = R_PREFIX + "binDir";

	public abstract String getRscript();
}
