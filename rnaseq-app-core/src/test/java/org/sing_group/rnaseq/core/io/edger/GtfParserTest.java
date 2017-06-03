package org.sing_group.rnaseq.core.io.edger;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class GtfParserTest {
	public static final File GTF_1 = new File(
		"src/test/resources/data/gtf/genes.gtf");
	
	public static final Map<String, String> GTF_1_MAPPING = new HashMap<>();
	
	static {
		GTF_1_MAPPING.put("DDX11L4", "NA");
		GTF_1_MAPPING.put("DDX11L1", "DDX11L1");
		GTF_1_MAPPING.put("DDX11L2", "DDX11L2");
		GTF_1_MAPPING.put("DDX11L3", "DDX11L3");
	}

	public static final File GTF_2 = new File(
		"src/test/resources/data/gtf/genes2.gtf");
	
	public static final Map<String, String> GTF_2_MAPPING = new HashMap<>();
	
	static {
		GTF_2_MAPPING.put("NM_033031", "CCNB3");
		GTF_2_MAPPING.put("NM_001168362", "PCDH11X");
		GTF_2_MAPPING.put("NM_001168361", "PCDH11X");
	}
	
	@Test
	public void parseGetGeneIdtoGeneNameMap() throws IOException {
		assertEquals(GTF_1_MAPPING, GtfParser.getGeneIdtoGeneNameMap(GTF_1));
		assertEquals(GTF_2_MAPPING, GtfParser.getGeneIdtoGeneNameMap(GTF_2));
	}
}
