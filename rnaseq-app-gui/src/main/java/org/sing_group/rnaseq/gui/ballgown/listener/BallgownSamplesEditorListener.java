package org.sing_group.rnaseq.gui.ballgown.listener;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

public interface BallgownSamplesEditorListener extends EventListener {
	void onSampleEdited(ChangeEvent event);
	void onSampleAdded(ChangeEvent event);
	void onSampleRemoved(ChangeEvent event);
}
