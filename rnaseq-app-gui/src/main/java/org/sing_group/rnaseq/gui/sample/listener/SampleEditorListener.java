package org.sing_group.rnaseq.gui.sample.listener;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

public interface SampleEditorListener extends EventListener {
	void onSampleEdited(ChangeEvent event);
}
