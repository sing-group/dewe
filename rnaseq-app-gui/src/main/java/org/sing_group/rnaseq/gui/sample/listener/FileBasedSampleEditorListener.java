package org.sing_group.rnaseq.gui.sample.listener;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

public interface FileBasedSampleEditorListener extends EventListener {
	void onSampleEdited(ChangeEvent event);
}
