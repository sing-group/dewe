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
package org.sing_group.rnaseq.core.environment.execution.parameters.pathfindr;

import org.sing_group.rnaseq.api.environment.execution.parameters.pathfindr.PathfindRGeneSets;

/**
 * The default {@code PathfindRClusteringMethod} implementation. It is
 * implemented as an enum that defines the clustering method.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see <a href=
 *      "https://cran.r-project.org/web/packages/pathfindR/pathfindR.pdf">PathfindR
 *      reference manual</a>
 */
public enum DefaultPathfindRClusteringMethod
	implements PathfindRGeneSets {
	WARDD("ward.D", "ward.D method"),
	WARDD2("ward.D2", "ward.D2 method"),
	SINGLE("single", "single method"),
	COMPLETE("complete", "complete method"),
	AVERAGE("average", "average (= UPGMA) method"),
	MCQUITTY("mcquitty", "mcquitty (= WPGMA) method"),
	MEDIAN("median", "median (= WPGMC) method"),
	CENTROID("centroid", "centroid (= UPGMC) method");
	
	public static final String DEFAULT_VALUE_STR = "average";
	public static final DefaultPathfindRClusteringMethod DEFAULT_VALUE = AVERAGE;

	private String parameter;
	private String description;

	DefaultPathfindRClusteringMethod(String parameter, String description) {
		this.parameter = parameter;
		this.description = description;
	}

	@Override
	public String getParameter() {
		return parameter;
	}

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isValidValue() {
		return true;
	}

	@Override
	public String toString() {
		return this.parameter;
	}
}
