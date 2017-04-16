package org.sing_group.rnaseq.api.environment.execution.parameters;

public interface Parameter {

	abstract String getParameter();

	abstract String getValue();

	abstract String getDescription();

	abstract boolean isValidValue();
}
