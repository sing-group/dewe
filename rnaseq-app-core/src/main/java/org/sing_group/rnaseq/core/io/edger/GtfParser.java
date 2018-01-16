/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.core.io.edger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A class that helps parsing GTF files
 * (http://www.ensembl.org/info/website/upload/gff.html).
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class GtfParser {

	/**
	 * Writes the gene id to gene name mappings (obtained with
	 * {@link GtfParser#getGeneIdtoGeneNameMap(File)}) to the {@code output}
	 * file.
	 * 
	 * @param gtf the GTF file to parse
	 * @param output the output file to write the mapping
	 * @throws IOException if an error occurs while parsing the file
	 */
	public static void writeGeneIdToGeneNameMappings(File gtf, File output)
		throws IOException {
		writeMapping(getGeneIdtoGeneNameMap(gtf), output);
	}

	private static void writeMapping(Map<String, String> mapping, File output)
		throws IOException {
		List<String> lines = new LinkedList<>();
		mapping.forEach((k, v) -> {
			lines.add(k + "\t" + v);
		});
		Files.write(output.toPath(), lines);
	}

	/**
	 * Parses {@code gtf} file in order to create a map from {@code gene_id}s to
	 * {@code gene_name}s.
	 * 
	 * @param gtf the GTF file to parse
	 * @return a map from {@code gene_id}s to {@code gene_name}s
	 * @throws IOException if an error occurs while parsing the file
	 */
	public static Map<String, String> getGeneIdtoGeneNameMap(File gtf)
		throws IOException {
		Map<String, String> map = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(gtf))) {
			String line;
			while ((line = reader.readLine()) != null) {
				parseGtfLine(line, map);
			}
		} catch (final IOException e) {
			throw e;
		}
		return map;
	}

	private static void parseGtfLine(String line, Map<String, String> map)
		throws IOException {
		Optional<String> geneId = extractAttribute(line, "gene_id");
		if (geneId.isPresent()) {
			Optional<String> geneName = extractAttribute(line, "gene_name");
			map.put(geneId.get(), geneName.orElse("NA"));
		}
	}

	private static Optional<String> extractAttribute(String line,
		String attributeId)
		throws IOException {
		String[] columns = line.split("\t");
		if (columns.length != 9) {
			throw new IOException(
				"Bad GTF format: each feature line must have 9 columns");
		} else {
			String attributesStr = columns[8];
			String[] attributes = attributesStr.split(";");
			for (String attribute : attributes) {
				attribute = attribute.trim();
				if (attribute.startsWith(attributeId)) {
					String attributeValue = attribute.substring(
						attribute.indexOf("\"") + 1, 
						attribute.length()-1
					);
					return Optional.of(attributeValue);
				}
			}
		}
		return Optional.empty();
	}
}
