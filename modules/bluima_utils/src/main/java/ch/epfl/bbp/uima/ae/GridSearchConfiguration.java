package ch.epfl.bbp.uima.ae;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.uima.utils.StaticOption;

/**
 * <p>
 * Utility/hack to perform in-place (statically configured) parameter
 * configuration for grid-search optimization. NOT INTENDED FOR PROD, BUT WHEN
 * EXPLORING SCENARIOS.
 * <p>
 * Parameters are dynamically loaded, and have to be named "o1", "o2", ...
 * (order is not relevant). They must have the following format:<br>
 * <code>o{1 ...}: paramName{space}paramType{space}value1{space}value2...</code>
 * <p>
 * Examples:
 * <ul>
 * <li><code>o1: test1 bool</code></li>
 * <li><code>o2: test2 int 1 3 5</code></li>
 * <li><code>o2: test3 string a b asdf</code></li>
 * </ul>
 * Then, you can specify which combinaison to use with
 * {@link StaticConfiguration#COMBINAISON_INDEX}
 * 
 * @author renaud.richardet@epfl.ch
 */
public class GridSearchConfiguration extends JCasAnnotator_ImplBase {

    public static final String COMBINAISON_INDEX = "combinaisonIndex";
    @ConfigurationParameter(name = COMBINAISON_INDEX, mandatory = true, //
    description = "The index of the current option")
    private int combinaisonIndex;

    @Override
    public void initialize(UimaContext ctx)
            throws ResourceInitializationException {
        super.initialize(ctx);

        // dynamically loading parameters (names = "o1", "o2", ...)
        checkArgument(newArrayList(ctx.getConfigParameterNames())
                .contains("o1"), "there should at least be 'o1'");

        List<String> optionsStr = newArrayList();
        for (int i = 1; i < ctx.getConfigParameterNames().length; i++) {
            String param = (String) ctx.getConfigParameterValue("o" + i);
            optionsStr.add(param);
        }
        StaticOption.parseOptions(optionsStr);

        // from combinaisonIndex, find current option
        StaticOption.setChoosenOption(combinaisonIndex);
        
        StaticOption.print();
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        // nope
    }
}
