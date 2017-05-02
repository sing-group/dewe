package org.sing_group.rnaseq.gui.components.wizard.steps;

/**
 * An interface that defines a wizard summary provider.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface WizardSummaryProvider {

	/**
	 * Returns the wizards summary.
	 *
	 * @return the wizards summary
	 */
	public abstract String getSummary();
}
