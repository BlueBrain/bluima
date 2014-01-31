package ch.epfl.bbp.uima.pubmed;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.join;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.DateCreatedType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.MeshHeadingType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.QualifierNameType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PubmedSearchUtil {

    public static String getAbstract(PubmedArticleType article) {
        try {
            String abs = StringUtils.join(article.getMedlineCitation()
                    .getArticle().getAbstract().getAbstractText());
            return abs;
        } catch (Exception e) {// nope
        }
        return null;
    }

    public static String getJournal(PubmedArticleType article) {
        try {
            return article.getMedlineCitation().getArticle().getJournal()
                    .getTitle().toString();
        } catch (Exception e) {// nope
        }
        return null;
    }

    public static String[] getLanguages(PubmedArticleType article) {
        try {
            return article.getMedlineCitation().getArticle().getLanguage();
        } catch (Exception e) {// nope
        }
        return null;
    }

    public static String getLanguageJoin(PubmedArticleType article) {
        try {
            String[] languages = getLanguages(article);
            if (languages != null && languages.length > 0) {
                return join(languages, ",");
            }
        } catch (Exception e) {// nope
        }
        return null;
    }

    final static String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
    final static Pattern p = Pattern.compile(RE_MAIL);

    public static String getEmail(PubmedArticleType article) {
        try {
            String affiliation = article.getMedlineCitation().getArticle()
                    .getAffiliation();
            if (affiliation == null) {
                return null;
            } else {
                Matcher m = p.matcher(affiliation);
                if (m.find()) {
                    return m.group(1);
                }
            }
        } catch (Exception e) {// nope
        }
        return null;
    }

    public static String getTitle(PubmedArticleType article) {
        try {
            return article.getMedlineCitation().getArticle().getArticleTitle()
                    .getString();
        } catch (Exception e) {// nope
        }
        return null;
    }

    public static String getDate(PubmedArticleType article) {
        try {
            DateCreatedType d = article.getMedlineCitation().getDateCreated();
            return d.getYear() + "-" + d.getMonth() + "-" + d.getDay();
        } catch (Exception e) {// nope
        }
        return null;
    }

    public static Integer getPmId(PubmedArticleType article) {
        try {
            return parseInt(article.getMedlineCitation().getPMID().getString());
        } catch (Exception e) {// nope
        }
        return null;
    }

    public static List<MeshHeading> getMeshTerms(PubmedArticleType article) {
        List<MeshHeading> ret = Lists.newArrayList();

        MeshHeadingType[] meshHeading = article.getMedlineCitation()
                .getMeshHeadingList().getMeshHeading();
        for (MeshHeadingType mesh : meshHeading) {
            MeshHeading m = new MeshHeading();
            m.descriptor = mesh.getDescriptorName().toString();
            QualifierNameType[] qualifierName = mesh.getQualifierName();
            if (qualifierName != null) {
                for (QualifierNameType q : qualifierName) {
                    if (q.getMajorTopicYN().getValue().toString().equals("Y")) {
                        m.qualifiers.add(q.toString());
                    }
                }
            }
            ret.add(m);
        }
        return ret;
    }

    public static class MeshHeading {
        public String descriptor;
        public List<String> qualifiers = new ArrayList<String>();

        @Override
        public String toString() {
            return descriptor;
        }
    }

    public static Set<String> getArticleType(PubmedArticleType article) {
        try {
            return newHashSet(article.getMedlineCitation().getArticle()
                    .getPublicationTypeList().getPublicationType());
        } catch (Exception e) {// nope
        }
        return newHashSet();
    }

    public static boolean isTypeComment(PubmedArticleType article) {
        return getArticleType(article).contains("Comment");
    }

    public static boolean isTypeLetter(PubmedArticleType article) {
        return getArticleType(article).contains("Letter");
    }

    public static boolean isTypeEditorial(PubmedArticleType article) {
        return getArticleType(article).contains("Editorial");
    }

    public static boolean isTypeReview(PubmedArticleType article) {
        return getArticleType(article).contains("Review");
    }

    public static boolean isTypeJournalArticle(PubmedArticleType article) {
        return getArticleType(article).contains("Journal Article");
    }
}
