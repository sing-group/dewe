package org.sing_group.rnaseq.api.entities;

import java.io.Serializable;
import java.util.List;

/**
 * The interface that defines a list of {@code FastqReadsSample}s.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface FastqReadsSamples
	extends List<FastqReadsSample>, Serializable {
}
