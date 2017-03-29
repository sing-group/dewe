package org.sing_group.rnaseq.gui.workflow;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BoxLayout.Y_AXIS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXLabel;
import org.sing_group.rnaseq.api.entities.WorkflowDescription;

import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;

public class WorkflowCatalogPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final Color BG_COLOR = Color.WHITE;
	private List<WorkflowDescription> workflows;
	
	public WorkflowCatalogPanel(List<WorkflowDescription> workflows) {
		this.workflows = Objects.requireNonNull(workflows);

		this.init();
	}

	private void init() {
		this.setBackground(BG_COLOR);
		this.setLayout(new BorderLayout());
		this.add(getTitle(), NORTH);
		this.add(getDescriptionsPanel(), CENTER);
	}

	protected JComponent getTitle() {
		JXLabel label = new JXLabel(getWorkflowCatalogTitle());
		label.setFont(label.getFont().deriveFont(Font.BOLD, 15f));
		label.setBorder(createEmptyBorder(10, 10, 10, 10));
		return label;
	}

	protected String getWorkflowCatalogTitle() {
		return "Workflow catalog";
	}
	
	protected JComponent getDescriptionsPanel() {
		JPanel descriptionsPanel = new JPanel();
		descriptionsPanel.setOpaque(false);
		descriptionsPanel.setLayout(new GridLayout(workflows.size(), 2));
		workflows.forEach(w -> {
			descriptionsPanel.add(new WorkflowDescriptionWrapperPanel(w));
		});
		return wrapInCenterdPanel(descriptionsPanel);
	}

	class WorkflowDescriptionWrapperPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private WorkflowDescription workflow;

		public WorkflowDescriptionWrapperPanel(WorkflowDescription workflow) {
			this.workflow = workflow;

			this.init();
		}

		private void init() {
			this.setOpaque(false);
			this.setLayout(new GridLayout(1, 2));
			this.add(new WorkflowDescriptionPanel(workflow));
			this.add(getButtonsPanel());
			this.setBorder(createEmptyBorder(5, 5, 5, 5));
		}

		protected JComponent getButtonsPanel() {
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.setBorder(createEmptyBorder(0, 10, 0, 10));
			buttonsPanel.setOpaque(false);
			buttonsPanel.setLayout(new BoxLayout(buttonsPanel, Y_AXIS));
			buttonsPanel.add(new JButton(new AbstractAction("Run workflow") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					runWorkflow(workflow);
				}
			}));
			return buttonsPanel;
		}
	}

	private JComponent wrapInCenterdPanel(JComponent c) {
		CenteredJPanel centeredPanel = new CenteredJPanel(c);
		centeredPanel.setOpaque(false);
		return centeredPanel;
	}

	protected void runWorkflow(WorkflowDescription workflow) {
	}
}
