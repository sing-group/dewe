/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class SampleReadsSelectionStepTest {

	public static void main(String[] args) {
		SampleReadsSelectionStep step = 
			new SampleReadsSelectionStep(new ExperimentalConditionsStep(1) {
				@Override
				public List<String> getExperimentalConditions() {
					return new LinkedList<>(Arrays.asList("A", "B"));
				}
			},
			1, 2);
		step.addWizardStepListener(new DefaultWizardStepListener());
		TestUtils.showStepComponent(step);
	}
}
