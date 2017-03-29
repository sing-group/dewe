package org.sing_group.rnaseq.gui.workflow;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;
import static java.util.Arrays.asList;

import javax.swing.JComponent;

public class WorkflowCatalogPanelTest {

	public static void main(String[] args) {
		setNimbusLookAndFeel();
		showComponent(createComponent());
	}

	private static JComponent createComponent() {
		return new WorkflowCatalogPanel(asList(
			new Bowtie2StringTieDifferentialExpressionWorkflowDescription(),
			new Hisat2StringTieBallgownDifferentialExpressionWorkflowDescription()
		));
	}
}
