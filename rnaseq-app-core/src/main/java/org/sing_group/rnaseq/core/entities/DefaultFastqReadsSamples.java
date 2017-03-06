package org.sing_group.rnaseq.core.entities;

import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;

public class DefaultFastqReadsSamples 
	extends LinkedList<FastqReadsSample> implements FastqReadsSamples
{
	private static final long serialVersionUID = 1L;

	public DefaultFastqReadsSamples(List<FastqReadsSample> samples) {
		super(samples);
	}
}
