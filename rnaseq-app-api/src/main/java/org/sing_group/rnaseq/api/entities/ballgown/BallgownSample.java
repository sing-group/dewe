package org.sing_group.rnaseq.api.entities.ballgown;

import java.io.File;
import java.io.Serializable;

public interface BallgownSample extends Serializable {

	abstract String getName();

	abstract String getType();

	abstract File getDirectory();
}
