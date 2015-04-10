package sml;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.ucla.sspace.clustering.Assignments;
import edu.ucla.sspace.clustering.CKVWSpectralClustering06;
import edu.ucla.sspace.matrix.Matrix;


public class TestSpectralClustering {

	public List<Set<Integer>> returnAssignmentsAfterSpectralClustering(Matrix matrix) {
		CKVWSpectralClustering06 spectralClusterer = new CKVWSpectralClustering06();
		
		Assignments assignments =spectralClusterer.cluster(matrix, new Properties());
		return assignments.clusters();
	}
}
