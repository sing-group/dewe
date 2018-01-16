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
package org.sing_group.rnaseq.gui.sample;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.entities.FileBasedSample;
import org.sing_group.rnaseq.api.entities.FileBasedSamples;

/**
 * An abstract, generic extension of a {@code FileBasedSamplesEditor} to control
 * experiment settings such as the required number of conditions and the
 * required number of samples per condition.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <T> the type of the list
 * @param <E> the type of the elements in the list
 */
public abstract class ExperimentalConditionsSamplesSelection<T extends FileBasedSamples<E>, E extends FileBasedSample>
	extends FileBasedSamplesEditor<T, E> {
	private static final long serialVersionUID = 1L;

	private static final String WARNING_SAMPLES_CONDITION =
		"At least two samples must be assigned to each condition.";

	private static final int DEFAULT_NUM_CONDITIONS = 2;
	private static final int DEFAUT_MIN_SAMPLES_PER_CONDITION = 2;
	private int minSamplesPerCondition;
	private int numConditions;

	private boolean isCheckingExperimentalSetup = false;

	/**
	 * Creates a new {@code ExperimentalConditionsSamplesSelection} component
	 * that requires 2 conditions with at least 2 samples each.
	 */
	public ExperimentalConditionsSamplesSelection() {
		this(DEFAULT_NUM_CONDITIONS, DEFAUT_MIN_SAMPLES_PER_CONDITION);
	}

	/**
	 * Creates a new {@code ExperimentalConditionsSamplesSelection} component
	 * with the specified parameters.
	 *
	 * @param numConditions the required number of conditions
	 * @param minSamplesPerCondition the minimum number of samples required in
	 *        each condition
	 */
	public ExperimentalConditionsSamplesSelection(int numConditions,
		int minSamplesPerCondition
	) {
		this.numConditions = numConditions;
		this.minSamplesPerCondition = minSamplesPerCondition;
	}

	@Override
	public boolean isValidSelection() {
		if (super.isValidSelection()) {
			if (!isCheckingExperimentalSetup) {
				return checkExperimentalSetup();
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	private boolean checkExperimentalSetup() {
		isCheckingExperimentalSetup = true;
		boolean conditionCount = checkConditionCount();
		boolean samplesPerCondition = checkSamplesPerCondition();
		List<String> duplicatedSampleNames = findDuplicateSampleNames();
		boolean allSampleNamesAreUnique = duplicatedSampleNames.isEmpty();

		if (conditionCount && samplesPerCondition && allSampleNamesAreUnique) {
			this.removeWarningMessages();
			isCheckingExperimentalSetup = false;
			return true;
		} else {
			final List<String> warningMessages = getWarningMessages(
				conditionCount, samplesPerCondition, duplicatedSampleNames);
			this.setWarningMessages(warningMessages);
			isCheckingExperimentalSetup = false;
			return false;
		}
	}

	private List<String> findDuplicateSampleNames() {
		Map<Object, Long> counts = this.getSamples().stream()
			.map(FileBasedSample::getName)
			.collect(groupingBy(e -> e, counting()));

		List<String> duplicatedNames = new LinkedList<>();
		for (Entry<Object, Long> entry : counts.entrySet()) {
			if (entry.getValue() > 1) {
				duplicatedNames.add(entry.getKey().toString());
			}
		}

		return duplicatedNames;
	}

	private List<String> getWarningMessages(boolean conditionCount,
		boolean samplesPerCondition, List<String> duplicatedSampleNames
	) {
		boolean allSampleNamesAreUnique = duplicatedSampleNames.isEmpty();
		final List<String> warningMessages = new ArrayList<>();

		if (!conditionCount) {
			warningMessages
				.add("You must introduce " + numConditions + " conditions");
		}

		if (!samplesPerCondition) {
			warningMessages.add(WARNING_SAMPLES_CONDITION);
		}

		if(!allSampleNamesAreUnique) {
			duplicatedSampleNames.stream()
				.map(n -> "Sample name '" + n + " 'is duplicated.")
				.forEach(n -> warningMessages.add(n));
		}

		return warningMessages;
	}

	private boolean checkConditionCount() {
		return this.getSamples().stream().map(FileBasedSample::getType)
			.collect(Collectors.toSet()).size() == numConditions;
	}

	private boolean checkSamplesPerCondition() {
		Map<String, Long> counts = this.getSamples().stream()
			.map(FileBasedSample::getType)
			.collect(groupingBy(e -> e, counting()));

		return eachConditionContainsMinimumSamples(counts);
	}

	private boolean eachConditionContainsMinimumSamples(
		Map<String, Long> counts
	) {
		return !counts.values().stream()
				.filter(numSamples -> numSamples < minSamplesPerCondition)
				.findAny().isPresent();
	}
}
