package org.sing_group.rnaseq.core.entities;

import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.entities.FileBasedSample;
import org.sing_group.rnaseq.api.entities.FileBasedSamples;

public class DefaultFileBasedSamples<T extends FileBasedSample> 
	extends LinkedList<T>
	implements FileBasedSamples<T> 
{
	private static final long serialVersionUID = 1L;

	public DefaultFileBasedSamples(List<T> samples) {
		super(samples);
	}
}
