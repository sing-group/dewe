package org.sing_group.rnaseq.api.entities;

/**
 * The description of a workflow.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface WorkflowDescription {

	/**
	 * Returns the title of the workflow.
	 * 
	 * @return the title of the workflow
	 */
	public abstract String getTitle();

	/**
	 * Returns a short description of the workflow.
	 * 
	 * @return a short description of the workflow
	 */
	public abstract String getShortDescription();
}
