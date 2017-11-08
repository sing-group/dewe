/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.gui.sample;

import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import java.util.Arrays;

import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;

public class FastqSingleEndSampleEditorTest {

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
		FastqSampleEditor editor = new FastqSampleEditor(false);
		editor.addSampleEditorListener(new SampleEditorListener() {
			
			@Override
			public void onSampleEdited(ChangeEvent event) {
				System.err.println("Sample edited. Is valid value? " + editor.isValidValue());
				System.err.println(editor.getSample());
				System.err.println();
			}
		});
		return editor;
	}
}
