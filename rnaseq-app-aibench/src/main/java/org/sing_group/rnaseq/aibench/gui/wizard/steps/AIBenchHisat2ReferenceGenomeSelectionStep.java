package org.sing_group.rnaseq.aibench.gui.wizard.steps;

import static javax.swing.BorderFactory.createEmptyBorder;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard.BUILD_INDEX;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard.IMPORT_INDEX;
import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.steps.Hisat2ReferenceGenomeSelectionStep;

import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;

/**
 * An extension of {@code Hisat2ReferenceGenomeSelectionStep} that adds buttons
 * to import or build genome indexes using AIBench operations.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchHisat2ReferenceGenomeSelectionStep
	extends Hisat2ReferenceGenomeSelectionStep {
	private CenteredJPanel stepComponent;
	
	/**
	 * Creates a new {@code AIBenchHisat2ReferenceGenomeSelectionStep} using
	 * {@code DefaultReferenceGenomeDatabaseManager}.
	 */
	public AIBenchHisat2ReferenceGenomeSelectionStep() {
		this(DefaultReferenceGenomeDatabaseManager.getInstance());
	}
	
	/**
	 * Creates a new {@code AIBenchHisat2ReferenceGenomeSelectionStep} using the
	 * specified {@code databaseManager}.
	 * 
	 * @param databaseManager the {@code ReferenceGenomeDatabaseManager}
	 */
	public AIBenchHisat2ReferenceGenomeSelectionStep(
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
