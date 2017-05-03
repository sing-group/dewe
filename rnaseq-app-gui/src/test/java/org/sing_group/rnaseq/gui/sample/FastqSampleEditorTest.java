package org.sing_group.rnaseq.gui.sample;

import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import java.util.Arrays;

import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;

public class FastqSampleEditorTest {

	public static void main(String[] args) {
		setNimbusLookAndFeel();
		FastqSampleEditor editor = createComponent();
		showComponent(editor);

		try {
			Thread.sleep(2000);
			editor.setSelectableConditions(Arrays.asList("A", "B"));
		} catch (InterruptedException e) {
		}
	}

	private static FastqSampleEditor createComponent() {
		FastqSampleEditor editor = new FastqSampleEditor();
		editor.addSampleEditorListener(new SampleEditorListener() {
			
			@Override
			public void onSampleEdited(ChangeEvent event) {
				System.err.println(editor.getSample());
			}
		});
		return editor;
	}
}
