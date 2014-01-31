package ch.epfl.bbp.uima.pubmed.mesh;

import static com.google.common.base.Preconditions.checkArgument;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.MedlineCitationType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.MeshHeadingListType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.MeshHeadingType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.pubmed.PubmedSearch;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

public class FetchMeshsScript {
	public static void main(String[] args) throws Exception {

		PubmedSearch ps = new PubmedSearch();

		int neuronIds[] = LineReader
				.intsFrom(new FileInputStream(
						"/Volumes/HDD2/ren_data/dev_hdd/bluebrain/1_collect/aws/fetchlist_mesh/rrrrrabbit_fetchlist_meshNeuron.txt"));
		checkArgument(neuronIds.length > 100000);

		TextFileWriter writer = new TextFileWriter(new File("out.tsv"));

		int i = 0;
		for (List<Integer> partition : Lists.partition(Ints.asList(neuronIds),
				100)) {
			System.out.println("batch " + i++);
			try {
				Iterator<PubmedArticleType> articleIt = ps.search(
						StringUtils.join(partition, " "), 101);
				while (articleIt.hasNext()) {
					PubmedArticleType article = articleIt.next();
					MedlineCitationType mc = article.getMedlineCitation();

					String pubmedId = mc.getPMID().getString();
					String line = pubmedId;

					// MeSH
					MeshHeadingListType meshes = mc.getMeshHeadingList();
					if (meshes != null && meshes.getMeshHeading() != null) {
						for (MeshHeadingType mesh : meshes.getMeshHeading()) {
							String dn = mesh.getDescriptorName().toString();
							line += "\t" + dn;
						}
					}
					writer.addLine(line);
				}
			} catch (Exception e) {
				e.fillInStackTrace();
				ps = new PubmedSearch();
			}
			writer.flush();
		}
		writer.close();
	}
}
