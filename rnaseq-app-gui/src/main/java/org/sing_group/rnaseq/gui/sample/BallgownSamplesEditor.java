/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.sample;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSamples;

/**
 * A {@code FileBasedSamplesEditor} implementation to the introduction of a
 * {@code BallgownSamples} list of {@code BallgownSample}s.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownSamplesEditor extends
	ExperimentalConditionsSamplesSelection<BallgownSamples, BallgownSample> {
	private static final long serialVersionUID = 1L;

	@Override
	protected FileBasedSampleEditor<BallgownSample> getFileBasedSampleEditor() {
		return new BallgownSampleEditor();
	}

	@Override
	public BallgownSamples getSamples() {
		return new DefaultBallgownSamples(getSamplesList());
	}
}