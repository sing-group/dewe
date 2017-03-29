package org.sing_group.rnaseq.gui.workflow;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import javax.swing.JComponent;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;

public class WorkflowDescriptionPanelTest {

	public static void main(String[] args) {
		setNimbusLookAndFeel();
		showComponent(createComponent());
	}

	private static JComponent createComponent() {
		return new WorkflowDescriptionPanel(new WorkflowDescription() {

			@Override
			public String getTitle() {
				return "Workflow test title";
			}

			@Override
			public String getShortDescription() {
				return "Workflow test short description";
			}
		});
	}
}
