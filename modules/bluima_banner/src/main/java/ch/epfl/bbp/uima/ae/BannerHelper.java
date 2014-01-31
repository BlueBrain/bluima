package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.BLUE_UIMA_ROOT;
import static ch.epfl.bbp.uima.ae.DragonLemmatiserAnnotator.PARAM_LEMMATISER_DATA;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

public class BannerHelper {

    public final static String BANNER_ROOT = BLUE_UIMA_ROOT
            + "modules/bluima_banner/";

    public static AnalysisEngineDescription getLemmatizer()
            throws ResourceInitializationException {

        String lemmatizerData = BANNER_ROOT
                + "src/main/resources/pear_resources/nlpdata/lemmatiser";
        checkArgument(new File(lemmatizerData).exists(),
                "no lemmatizerData file");

        return createEngineDescription(DragonLemmatiserAnnotator.class,
                PARAM_LEMMATISER_DATA, lemmatizerData);
    }

    public static AnalysisEngineDescription getBanner()
            throws ResourceInitializationException {
        return createEngineDescription(BannerAnnotator.class);
    }
}
