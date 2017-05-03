package org.sing_group.rnaseq.gui.sample;

import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import java.util.Arrays;

import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

public class FastqSamplesEditorTest {

	public static void main(String[] args) {
		setNimbusLookAndFeel();
		FastqSamplesEditor editor = createComponent();
		showComponent(editor);

		try {
			Thread.sleep(2000);
			editor.setSelectableConditions(Arrays.asList("A", "B"));
		} catch (InterruptedException e) {
		}
	}

	private static FastqSamplesEditor createComponent() {
		FastqSamplesEditor editor = new FastqSamplesEditor(4);
		editor.addSamplesEditorListener(new SamplesEditorListener() {
			
			@Override
			public void onSampleRemoved(ChangeEvent event) {
				System.err.println("Sample removed");
			}
			
			@Override
			public void onSampleEdited(ChangeEvent event) {
				System.err.println("Sample edited");
			}
			
			@Override
			public void onSampleAdded(ChangeEvent event) {
				System.err.println("Sample added");
			}
		});
		return editor;
	}
}
