package org.sing_group.rnaseq.api.entities;

import java.io.File;
import java.io.Serializable;

public interface FileBasedSample extends Serializable {
	abstract String getName();

	abstract String getType();

	abstract File getFile();
}
