package org.sing_group.rnaseq.gui.ballgown.listener;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

public interface BallgownSampleEditorListener extends EventListener {
	void onSampleEdited(ChangeEvent event);
}
