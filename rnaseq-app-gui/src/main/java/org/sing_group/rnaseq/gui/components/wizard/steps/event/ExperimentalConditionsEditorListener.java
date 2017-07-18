package org.sing_group.rnaseq.gui.components.wizard.steps.event;

import java.util.EventListener;
import java.util.Map;

import javax.swing.event.ChangeEvent;

public interface ExperimentalConditionsEditorListener extends EventListener {

	public void experimentalConditionsChanged(ChangeEvent event);

	public void experimentalConditionsRenamed(ChangeEvent event, Map<String, String> renameMap);
}
