package org.sing_group.rnaseq.api.entities;

import java.io.Serializable;
import java.util.List;

public interface FileBasedSamples<T extends FileBasedSample> extends List<T>, Serializable {

}
