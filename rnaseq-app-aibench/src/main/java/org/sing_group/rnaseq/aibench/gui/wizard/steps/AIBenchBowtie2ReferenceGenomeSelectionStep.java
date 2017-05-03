package org.sing_group.rnaseq.aibench.gui.wizard.steps;

import static javax.swing.BorderFactory.createEmptyBorder;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchBowtieStringTieAndRDifferentialExpressionWizard.BUILD_INDEX;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchBowtieStringTieAndRDifferentialExpressionWizard.IMPORT_INDEX;
import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.steps.Bowtie2ReferenceGenomeSelectionStep;

import es.uvigo.ei.aibench.workbench.Workbench;

/**
 * An extension of {@code Bowtie2ReferenceGenomeSelectionStep} that adds buttons
 * to import or build genome indexes using AIBench operations.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchBowtie2ReferenceGenomeSelectionStep
	extends Bowtie2ReferenceGenomeSelectionStep {
	private CenteredJPanel stepComponent;

	/**
	 * Creates a new {@code AIBenchBowtie2ReferenceGenomeSelectionStep} using
	 * {@code DefaultReferenceGenomeDatabaseManager}.
	 */
	public AIBenchBowtie2ReferenceGenomeSelectionStep() {
		this(DefaultReferenceGenomeDatabaseManager.getInstance());
	}

	/**
	 * Creates a new {@code AIBenchBowtie2ReferenceGenomeSelectionStep} using the
	 * specified {@code databaseManager}.
	 * 
	 * @param databaseManager the {@code ReferenceGenomeDatabaseManager}
	 */
	public AIBenchBowtie2ReferenceGenomeSelectionStep(
		ReferenceGenomeDatabaseManager databaseManager
	) {
		super(databaseManager);
	}

	@Override
	public JComponent getStepComponent() {
		if(this.stepComponent == null) {
			this.stepComponent = createStepComponent();
			configureStepComponent(this.stepComponent);
		}
		return this.stepComponent;
	}

	private CenteredJPanel createStepComponent() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.add(getButtonsPane(), BorderLayout.NORTH);
		panel.add(super.getSelectionPanel(), BorderLayout.CENTER);

		return new CenteredJPanel(panel);
	}

	private JPanel getButtonsPane() {
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.setOpaque(false);
		buttonsPanel.setBorder(createEmptyBorder(0, 20, 10, 0));
		buttonsPanel.add(new JButton(
			new ExtendedAbstractAction("Import index", this::importIndex)));
		buttonsPanel.add(new JButton(
			new ExtendedAbstractAction("Build index", this::buildIndex)));
	
		return buttonsPanel;
	}

	private void importIndex() {
		Workbench.getInstance().executeOperationAndWait(IMPORT_INDEX);
	}

	private void buildIndex() {
		Workbench.getInstance().executeOperationAndWait(BUILD_INDEX);
	}
}
