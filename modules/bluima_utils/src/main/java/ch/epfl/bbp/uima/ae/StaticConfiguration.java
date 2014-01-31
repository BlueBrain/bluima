package ch.epfl.bbp.uima.ae;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.utils.StaticOption;

/**
 * Utility to perform in-place (statically configured) parameter configuration
 * for optimization.<br>
 * Parameters are set like this:<br>
 * <code>paramName{space}paramType{space}value1{space}value2...</code><br>
 * Examples:
 * <ul>
 * <li><code>o1: myparam1 bool true</code></li>
 * <li><code>o2: myparam2 int 3</code></li>
 * <li><code>o3: myparam2 dble 3.14</code></li>
 * <li><code>o4: myparam3 string asdf</code></li>
 * </ul>
 * <br>
 * Then, in your java code you can retreive the vales using the
 * {@link StaticOption.getXXX} methods
 * 
 * @author renaud.richardet@epfl.ch
 */
public class StaticConfiguration extends JCasAnnotator_ImplBase {

    @Override
    /** dynamically loading parameters (names = "o1", "o2", ...)*/
    public void initialize(UimaContext ctx)
            throws ResourceInitializationException {
        super.initialize(ctx);

        checkArgument(ctx.getConfigParameterNames().length > 0,
                "there should at least be one config parameter set");

        List<String> optionsStr = newArrayList();
        for (String paramName : ctx.getConfigParameterNames()) {
            String param = (String) ctx.getConfigParameterValue(paramName);
            optionsStr.add(param);
        }

        StaticOption.parseConfiguration(optionsStr);
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        // nope
    }
}
