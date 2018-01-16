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
package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.StringTieEnvironment;

/**
 * The default {@code StringTieEnvironment} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultStringTieEnvironment implements StringTieEnvironment {

	private static DefaultStringTieEnvironment INSTANCE;

	/**
	 * Returns the {@code DefaultStringTieEnvironment} singleton instance.
	 * 
	 * @return the {@code DefaultStringTieEnvironment} singleton instance
	 */
	public static DefaultStringTieEnvironment getInstance() {
		if
		(INSTANCE == null) {
			INSTANCE = new DefaultStringTieEnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultStringTie() {
		return "stringtie";
	}
}
