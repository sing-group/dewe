/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.api.environment;

import java.util.Optional;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.FastQcBinaries;
import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;
import org.sing_group.rnaseq.api.environment.binaries.IgvBrowserBinaries;
import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.api.environment.binaries.TrimmomaticBinaries;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;

/**
 * The interface that defines the application environment.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface AppEnvironment {
	/**
	 * Returns the {@code Bowtie2Binaries}.
	 *
	 * @return the {@code Bowtie2Binaries}
	 */
	public abstract Bowtie2Binaries getBowtie2Binaries();

	/**
	 * Returns the {@code SamtoolsBinaries}.
	 *
	 * @return the {@code SamtoolsBinaries}
	 */
	public abstract SamtoolsBinaries getSamtoolsBinaries();

	/**
	 * Returns the {@code StringTieBinaries}.
	 *
	 * @return the {@code StringTieBinaries}
	 */
	public abstract StringTieBinaries getStringTieBinaries();

	/**
	 * Returns the {@code HtseqBinaries}.
	 *
	 * @return the {@code HtseqBinaries}
	 */
	public abstract HtseqBinaries getHtseqBinaries();

	/**
	 * Returns the {@code RBinaries}.
	 *
	 * @return the {@code RBinaries}
	 */
	public abstract RBinaries getRBinaries();

	/**
	 * Returns the {@code SystemBinaries}.
	 *
	 * @return the {@code SystemBinaries}
	 */
	public abstract SystemBinaries getSystemBinaries();

	/**
	 * Returns the {@code Hisat2Binaries}.
	 *
	 * @return the {@code Hisat2Binaries}
	 */
	public abstract Hisat2Binaries getHisat2Binaries();

	/**
	 * Returns the {@code FastQcBinaries}.
	 *
	 * @return the {@code FastQcBinaries}
	 */
	public abstract FastQcBinaries getFastQcBinaries();

	/**
	 * Returns the {@code TrimmomaticBinaries}.
	 *
	 * @return the {@code TrimmomaticBinaries}
	 */
	public abstract TrimmomaticBinaries getTrimmomaticBinaries();

	/**
	 * Returns the {@code IgvBrowserBinaries}.
	 *
	 * @return the {@code IgvBrowserBinaries}
	 */
	public abstract IgvBrowserBinaries getIgvBrowserBinaries();

	/**
	 * Returns the {@code ReferenceGenomeIndexDatabaseManager}.
	 *
	 * @return the {@code ReferenceGenomeIndexDatabaseManager}
	 */
	public abstract ReferenceGenomeIndexDatabaseManager getReferenceGenomeDatabaseManager();

	/**
	 * Returns the value of the specified property, wrapped as an optional which
	 * can be empty if the property is not found.
	 *
	 * @param propertyName the name of the property to look for
	 * @return the value of the specified property.
	 */
	public abstract Optional<String> getProperty(String propertyName);

	/**
	 * Returns {@code true} if the environment (system or application
	 * configuration) contains a property with the specified name and
	 * {@code false} otherwise.
	 *
	 * @param propertyName the name of the property to look for
	 * @return {@code true} if the environment contains a property with the
	 *         specified name and {@code false} otherwise.
	 */
	public abstract boolean hasProperty(String propertyName);

	/**
	 * Returns the configured number of threads.
	 *
	 * @return the configured number of threads
	 */
	public abstract int getThreads();
}
