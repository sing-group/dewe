/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.core.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.sing_group.rnaseq.core.io.alignment.AlignmentTestSuite;
import org.sing_group.rnaseq.core.io.ballgown.BallgownTestSuite;
import org.sing_group.rnaseq.core.io.edger.EdgeRTestSuite;
import org.sing_group.rnaseq.core.io.samples.SamplesTestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	AlignmentTestSuite.class,
	BallgownTestSuite.class,
	EdgeRTestSuite.class,
	SamplesTestSuite.class
})
public class IOTestSuite {

}
