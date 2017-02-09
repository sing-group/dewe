package org.sing_group.rnaseq.gui.util;
import javax.swing.JFileChooser;

/**
 * A singleton class that provides a common file chooser to all the gui
 * components that request it. It is useful when integrating the application
 * into AIBench since the file chooser can be set using the
 * {@link CommonFileChooser#setSingleFilechooser(JFileChooser)} method.
 * 
 * @author hlfernandez
 *
 */
public class CommonFileChooser {
	private static final JFileChooser DEFAULT_FILECHOOSER = new JFileChooser(".");
	private static CommonFileChooser INSTANCE = null;
	private JFileChooser singlefilechooser; 
    
	private CommonFileChooser() {
		singlefilechooser = DEFAULT_FILECHOOSER;
	}

    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new CommonFileChooser();
        }
    }
 
    /**
     * Returns an instance of {@code CommonFileChooser}.
     * 
     * @return an instance of {@code CommonFileChooser}.
     */
	public static CommonFileChooser getInstance() {
		if (INSTANCE == null) {
			createInstance();
		}
		return INSTANCE;
	}

	/**
	 * Returns a {@code JFileChooser}.
	 * 
	 * @return a {@code JFileChooser}.
	 */
	public JFileChooser getSingleFilechooser() {
		return singlefilechooser;
	}

	/**
	 * Establishes the single {@code JFileChooser}.
	 * 
	 * @param filechooser the single {@code JFileChooser} to use.
	 */
	public void setSingleFilechooser(JFileChooser filechooser) {
		this.singlefilechooser = filechooser;
	}
}
