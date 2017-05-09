package org.sing_group.rnaseq.api.environment;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;
import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;

public interface AppEnvironment {
	public abstract Bowtie2Binaries getBowtie2Binaries();

	public abstract SamtoolsBinaries getSamtoolsBinaries();

	public abstract StringTieBinaries getStringTieBinaries();

	public abstract HtseqBinaries getHtseqBinaries();

	public abstract RBinaries getRBinaries();

	public abstract SystemBinaries getSystemBinaries();

	public abstract Hisat2Binaries getHisat2Binaries();

	public abstract ReferenceGenomeIndexDatabaseManager getReferenceGenomeDatabaseManager();

	public abstract String getProperty(String propertyName);

	public abstract boolean hasProperty(String propertyName);

	public abstract int getThreads();
}
