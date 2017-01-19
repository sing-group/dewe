package org.sing_group.rnaseq.api.environment.binaries;

public interface StringTieBinaries extends Binaries {
	public final static String STRINGTIE_PREFIX = "stringtie.";
	public final static String BASE_DIRECTORY_PROP = STRINGTIE_PREFIX + "binDir";

	public abstract String getStringTie();
}
