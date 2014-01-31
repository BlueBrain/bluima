package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.StringUtils.nullToEmpty;
import static ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.getAbstract;
import static ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.getTitle;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.MedlineCitationType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.MeshHeadingListType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.MeshHeadingType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.QualifierNameType;

import java.io.IOException;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.CollectionReaderFactory;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.pubmed.PubmedSearch;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Header;
import de.julielab.jules.types.MeshHeading;

/**
 * CollectionReader that connects to Pubmed's remote search service (webservice
 * API). Allows to query for a specific term (e.g. "cat") or mesh terms (e.g.
 * "brain[mesh]").
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TypeSystem.HEADER })
public class PubmedWebServiceCollectionReader extends
        JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory
            .getLogger(PubmedWebServiceCollectionReader.class);

    public static final String COMPONENT_ID = PubmedWebServiceCollectionReader.class
            .getName();

    @ConfigurationParameter(name = BlueUima.PARAM_QUERY, mandatory = true, //
    description = "the query parameter for entrez. E.g. 'cat' or 'brain[mesh]' "
            + "or '16381840[pmid]' or '17170002 16381840' (for several PMIDs) ")
    private String query;

    @ConfigurationParameter(name = BlueUima.PARAM_MAX_NR_RESULTS, mandatory = true, //
    defaultValue = "100", description = "maximum number of results that should be returned")
    private int maxNrResults;

    private Iterator<PubmedArticleType> resultsIt;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        try {
            PubmedSearch pubmedSearch = new PubmedSearch();
            resultsIt = pubmedSearch.search(query, maxNrResults);

        } catch (Exception e) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS,
                    new Object[] { BlueUima.PARAM_QUERY + " :" + query }, e);
        }
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        PubmedArticleType article = resultsIt.next();
        MedlineCitationType mc = article.getMedlineCitation();

        String title = nullToEmpty(getTitle(article));

        // header
        String pubmedId = mc.getPMID().getString();
        Header header = new Header(jcas);
        header.setDocId(pubmedId);
        header.setTitle(title);
        header.setComponentId(PubmedWebServiceCollectionReader.COMPONENT_ID);
        header.addToIndexes();

        // MeSH
        MeshHeadingListType meshes = mc.getMeshHeadingList();
        if (meshes != null && meshes.getMeshHeading() != null) {
            for (MeshHeadingType mesh : meshes.getMeshHeading()) {
                MeshHeading m = new MeshHeading(jcas);
                m.setDescriptorName(mesh.getDescriptorName().toString());
                QualifierNameType[] qualifierName = mesh.getQualifierName();
                if (qualifierName != null) {
                    for (QualifierNameType q : qualifierName) {
                        if (q.getMajorTopicYN().getValue().toString()
                                .equals("Y")) {
                            m.setQualifierName(q.toString());
                        }
                    }
                }
                m.addToIndexes();
            }
        }

        jcas.setDocumentText(nullToEmpty(getAbstract(article)));
    }

    public boolean hasNext() throws IOException, CollectionException {
        return resultsIt.hasNext();
    }

    public Progress[] getProgress() {// nope
        return null;
    }

    public static CollectionReader getCR(String query)
            throws ResourceInitializationException {
        return getCR(query, Integer.MAX_VALUE);
    }

    public static CollectionReader getCR(String query, int nrResults)
            throws ResourceInitializationException {
        return CollectionReaderFactory.createReader(
                PubmedWebServiceCollectionReader.class, TypeSystem.JULIE_TSD,
                BlueUima.PARAM_MAX_NR_RESULTS, nrResults,//
                BlueUima.PARAM_QUERY, query);
    }
}
