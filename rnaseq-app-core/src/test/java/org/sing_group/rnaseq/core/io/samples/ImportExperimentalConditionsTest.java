/*
 * #%L
 * DEWE Core
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
package org.sing_group.rnaseq.core.io.samples;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;

import junit.framework.Assert;

public class ImportExperimentalConditionsTest {

	public static final File DATA_DIR = new File(
		"src/test/resources/data/samples/import-PE-directory");
	private static final Set<String> EXPECTED_CONDITIONS =
		new HashSet<>(asList("conditionA", "conditionB", "conditionC"));

	@Test
	public void importExperimentalConditionsTest() {
		Map<String, FastqReadsSamples> conditions = ImportExperimentalConditions
			.importDirectory(DATA_DIR, true);
		Assert.assertEquals(EXPECTED_CONDITIONS, conditions.keySet());
	}
}
