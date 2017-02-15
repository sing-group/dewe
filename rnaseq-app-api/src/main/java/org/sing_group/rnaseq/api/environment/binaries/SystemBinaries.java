package org.sing_group.rnaseq.api.environment.binaries;

public interface SystemBinaries extends Binaries {
	public final static String SYSTEM_PREFIX = "system.";
	public final static String BASE_DIRECTORY_PROP = SYSTEM_PREFIX + "binDir";
	public final static String BASE_DIRECTORY_2_PROP = SYSTEM_PREFIX + "binDir2";

	public abstract String getJoin();

	public abstract String getSed();

	public abstract String getEnsgidsToSymbols();
}
