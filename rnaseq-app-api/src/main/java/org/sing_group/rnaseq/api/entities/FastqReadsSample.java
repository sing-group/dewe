package org.sing_group.rnaseq.api.entities;

import java.io.File;
import java.io.Serializable;

public interface FastqReadsSample extends Serializable {

	abstract String getName();

	abstract String getCondition();

	abstract File getReadsFile1();

	abstract File getReadsFile2();
}
