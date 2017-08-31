package org.sing_group.rnaseq.api.entities;

import java.io.Serializable;
import java.util.List;

/**
 * The interface that defines a list of {@code FileBasedSample}s.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface FileBasedSamples<T extends FileBasedSample>
	extends List<T>, Serializable {
}
