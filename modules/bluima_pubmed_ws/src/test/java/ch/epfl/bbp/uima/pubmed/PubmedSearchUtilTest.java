package ch.epfl.bbp.uima.pubmed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.MeshHeading;

import com.google.common.collect.Lists;

public class PubmedSearchUtilTest {
    @Test
    public void testTitleAbstract() throws Exception {

        PubmedArticleType a = Lists.newArrayList(
                new PubmedSearch().search("20923604")).get(0);
        assertNotNull(a);

        String abstrct = PubmedSearchUtil.getAbstract(a);
        assertEquals(
                "Spinal muscular atrophy (SMA) is an autosomal recessive disease characterized by degeneration and loss of lower motor neurons in the spinal cord and brainstem. Clinically, SMA has been classified into four types, according to the maximum function attained. The disease is caused by deletion or mutation of the telomeric copy of the SMN gene (SMN1), and the clinical severity is in part determined by the copy number of the centromeric SMN gene (SMN2). The SMN2 mRNA lacks exon 7, resulting in reduced production of the full-length SMN protein. Treatment of SMA consists of supportive care, although many drugs have been demonstrated to improve muscle strength and motor function of patients. The development of animal models of SMA has led to better interpretation of the physiopathology of the disease and testing of potential drug targets. Several mechanisms have been targeted in SMA drug trials, including neuroprotection, neurogenesis, energy metabolism improvement, anabolic stimulation and increment of SMN2 transcripts. Gene therapy and cell transplantation have also been tested in murine SMA.",
                abstrct);

        String title = PubmedSearchUtil.getTitle(a);
        assertEquals(
                "Spinal muscular atrophy: from animal model to clinical trial.",
                title);
        String date = PubmedSearchUtil.getDate(a);
        assertEquals("2010-10-06", date);

        List<MeshHeading> meshTerms = PubmedSearchUtil.getMeshTerms(a);
        assertTrue(meshTerms.get(0).descriptor.equals("Animals"));

        Set<String> type = PubmedSearchUtil.getArticleType(a);
        
        assertEquals(2, type.size());
        assertTrue(PubmedSearchUtil.isTypeReview(a));
    }

    // @Test
    // public void testNullAbstract() throws Exception {
    //
    // PubmedArticleType a = Lists.newArrayList(
    // new PubmedSearch().search("23441931")).get(0);
    // assertNotNull(a);
    //
    // String abstrct = PubmedSearchUtil.getAbstract(a);
    // assertEquals(
    // "Spinal muscular atrophy (SMA) is an autosomal recessive disease characterized by degeneration and loss of lower motor neurons in the spinal cord and brainstem. Clinically, SMA has been classified into four types, according to the maximum function attained. The disease is caused by deletion or mutation of the telomeric copy of the SMN gene (SMN1), and the clinical severity is in part determined by the copy number of the centromeric SMN gene (SMN2). The SMN2 mRNA lacks exon 7, resulting in reduced production of the full-length SMN protein. Treatment of SMA consists of supportive care, although many drugs have been demonstrated to improve muscle strength and motor function of patients. The development of animal models of SMA has led to better interpretation of the physiopathology of the disease and testing of potential drug targets. Several mechanisms have been targeted in SMA drug trials, including neuroprotection, neurogenesis, energy metabolism improvement, anabolic stimulation and increment of SMN2 transcripts. Gene therapy and cell transplantation have also been tested in murine SMA.",
    // abstrct);
    //
    // }
}
