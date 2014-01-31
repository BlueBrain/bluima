package ch.epfl.bbp.uima.ae;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.annotator.AnnotatorContextException;
import org.apache.uima.annotator.regex.Annotation;
import org.apache.uima.annotator.regex.Concept;
import org.apache.uima.annotator.regex.ConceptFileParser;
import org.apache.uima.annotator.regex.Feature;
import org.apache.uima.annotator.regex.FilterFeature;
import org.apache.uima.annotator.regex.Rule;
import org.apache.uima.annotator.regex.RuleException;
import org.apache.uima.annotator.regex.impl.ConceptFileParser_impl;
import org.apache.uima.annotator.regex.impl.Concept_impl;
import org.apache.uima.annotator.regex.impl.RegexAnnotatorConfigException;
import org.apache.uima.annotator.regex.impl.RegexAnnotatorProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import de.julielab.jules.types.Token;

/**
* Main RegEx annotator implementation class.
* 
* 
* ren: changed file resolution<br>
* ren: changed process(), adding ⊂⊃ btw all T
*/
public class RegExAnnotator extends CasAnnotator_ImplBase {
	
 public static final String TOKEN_SEPARATOR = "⊂⊃";

 public static final String MESSAGE_DIGEST = "org.apache.uima.annotator.regex.regexMessages";

 public static final String REGEX_CONCEPTS_FILES = "ConceptFiles";

 public static final String PATH_SEPARATOR = System.getProperty("path.separator");

 private Logger logger;

 private Concept[] regexConcepts;

 private boolean lastRuleExceptionMatch = false;

 private AnnotationFS lastRuleExceptionAnnotation = null;

 private NumberFormat floatNumberFormat = null;

 private NumberFormat integerNumberFormat = null;

 /**
  * Performs any startup tasks required by this annotator. This implementation reads the
  * configuration parameters and compiles the regular expressions.
  */
 public void initialize(UimaContext aContext) throws ResourceInitializationException {
   super.initialize(aContext);

   // initialize annotator logger
   this.logger = getContext().getLogger();

   // default initialization for number format
   this.floatNumberFormat = NumberFormat.getNumberInstance();
   this.integerNumberFormat = NumberFormat.getIntegerInstance();

   // create a concept file parser object
   ConceptFileParser parser = new ConceptFileParser_impl();

   // get configuration parameter settings
   // get parameter ConceptFiles, default is an empty array
   String[] conceptFileNames = safeGetConfigParameterStringArrayValue(getContext(),
       REGEX_CONCEPTS_FILES, new String[] {});

   // get UIMA datapath and tokenize it into its elements
   StringTokenizer tokenizer = new StringTokenizer(getContext().getDataPath(), PATH_SEPARATOR);
   ArrayList<File> datapathElements = new ArrayList<File>();
   while (tokenizer.hasMoreTokens()) {
     // add datapath elements to the 'datapathElements' array list
     datapathElements.add(new File(tokenizer.nextToken()));
   }

   // try to resolve the concept file names
   ArrayList<Concept> concepts = new ArrayList<Concept>();
   for (int i = 0; i < conceptFileNames.length; i++) {
     // try to resolve the relative file name with classpath or datapath
     String filename = conceptFileNames[i];
     List<ConceptFile> cfList = new ArrayList<ConceptFile>();
     if (containsWildcardChar(filename)) {
       resolveRelativeWildcardFilePath(filename, datapathElements, cfList);
     } else {
       ConceptFile file = resolveRelativeFilePath(filename, datapathElements);
       // if the current concept file wasn't found, throw an exception
       if (file == null) {
         throw new RegexAnnotatorConfigException("regex_annotator_resource_not_found",
             new Object[] { conceptFileNames[i] });
       }
       cfList.add(file);
       // log concept file path
       this.logger.logrb(Level.CONFIG, "RegExAnnotator", "initialize", MESSAGE_DIGEST,
           "regex_annotator_rule_set_file", new Object[] { file.getFilePath() });

     }

     for (ConceptFile file : cfList) {
       // parse concept file to internal objects
       Concept[] currentConcepts = parser.parseConceptFile(file.getFilePath(), file.getStream());
       try {
         file.getStream().close();
       } catch (IOException e) {
         this.logger.logrb(Level.WARNING, "RegExAnnotator", "initialize", MESSAGE_DIGEST,
             "regex_annotator_error_closing_input_stream", new Object[] { file.getFilePath(),
                 e.getMessage() });
       }
       // add all concepts to the concepts list
       for (int c = 0; c < currentConcepts.length; c++) {
         concepts.add(currentConcepts[c]);
       }
     }
   }

   // get one array that contains all the concepts
   this.regexConcepts = concepts.toArray(new Concept[] {});

   // check duplicate concept names
   HashSet<String> conceptNames = new HashSet<String>(this.regexConcepts.length);
   for (int i = 0; i < this.regexConcepts.length; i++) {
     String name = this.regexConcepts[i].getName();
     // check if concept name was set, if not, skip concept
     if (name == null) {
       continue;
     }
     // concept name was set, check for duplicate concept names
     // duplicate concept names can occurs, just log a warning!
     if (conceptNames.contains(name)) {
       this.logger.logrb(Level.WARNING, "RegExAnnotator", "initialize", MESSAGE_DIGEST,
           "regex_annotator_warning_duplicate_concept_name", new Object[] { name });
     } else {
       // add concept name to the concept name list
       conceptNames.add(name);
     }
   }

   // initialize the regex concepts
   for (int i = 0; i < this.regexConcepts.length; i++) {
     ((Concept_impl) this.regexConcepts[i]).initialize(this.logger);
   }
 }

 /**
  * @param context
  * @param param
  * @param defaultValue
  * @return returns the boolean parameter value
  * @throws AnnotatorContextException
  */
 private static String[] safeGetConfigParameterStringArrayValue(UimaContext context, String param,
     String[] defaultValue) {
   String[] array = (String[]) context.getConfigParameterValue(param);
   if (array != null && array.length > 0) {
     return array;
   }
   return defaultValue;
 }

 private static final boolean containsWildcardChar(String filename) {
   final int pos = filename.lastIndexOf('/');
   if (pos >= 0) {
     filename = filename.substring(pos);
   }
   return filename.indexOf('*') >= 0;
 }

 private static final Pattern wildcardExpr2Regex(String wildcardExpr) {
   // Note: backslash must be the first character to be replaced, for obvious reasons.
   final String[] specialChars = new String[] { "\\", ".", "+", "(", ")", "?", "[", "]", "{", "}",
       "$", "^", "|" };
   for (String escape : specialChars) {
     wildcardExpr = wildcardExpr.replace(escape, "\\" + escape);
   }
   wildcardExpr = wildcardExpr.replace("*", ".*");
   return Pattern.compile(wildcardExpr);
 }

 /**
  * @param context
  * @param param
  * @param defaultValue
  * @return returns the boolean parameter value
  * @throws AnnotatorContextException
  */
 private ConceptFile resolveRelativeFilePath(String fileName, ArrayList<File> datapathElements) {

     
   try {
    InputStream stream = new FileInputStream(fileName);
    return new ConceptFile(fileName, stream);
} catch (FileNotFoundException e) {
   logger.log(Level.WARNING, fileName+", "+e.getMessage());
}  
     
   return null;

 }

 private static final class RegexFileFilter implements FileFilter {
   private Pattern pattern;
   private RegexFileFilter(Pattern pattern) {
     super();
     this.pattern = pattern;
   }
   public boolean accept(File file) {
     return this.pattern.matcher(file.getName()).matches();
   }
 }
 
 private void resolveRelativeWildcardFilePath(String wildcardExpr,
     List<File> datapathElements, List<ConceptFile> cfList) {

   // try to use the class loader to load the file resource
   if (datapathElements == null || datapathElements.size() == 0) {
     return;
   }
   Pattern pattern = wildcardExpr2Regex((new File(wildcardExpr)).getName());
   FileFilter regexFileFilter = new RegexFileFilter(pattern);
   // try to use the datapath to load the file resource
   for (File dpDir : datapathElements) {
     if (dpDir.isDirectory() && dpDir.canRead()) {
       dpDir = (new File(dpDir, wildcardExpr)).getParentFile();
       File[] files = dpDir.listFiles(regexFileFilter);
       if (files.length == 0) {
         continue;
       }
       for (File file : files) {
         InputStream stream;
         try {
           stream = new BufferedInputStream(new FileInputStream(file));
         } catch (FileNotFoundException ex) {
           continue;
         }
         ConceptFile conceptFile = new ConceptFile(file.getAbsolutePath(), stream);
         cfList.add(conceptFile);
       }
       break;
     }
   }
 }

 /**
  * Acquires references to CAS Type and Feature objects that are later used during the
  * {@link #process(CAS)} method.
  */
 public void typeSystemInit(TypeSystem aTypeSystem) throws AnalysisEngineProcessException {
   // initialize types for the regex concepts
   if (this.regexConcepts != null) {
     try {
       for (int i = 0; i < this.regexConcepts.length; i++) {
         ((Concept_impl) this.regexConcepts[i]).typeInit(aTypeSystem);
       }
     } catch (ResourceInitializationException ex) {
       throw new RegexAnnotatorProcessException(ex);
     }
   }
 }

 /**
  * Invokes this annotator's analysis logic. This annotator uses the java regular expression
  * package to find annotations using the regular expressions defined by its configuration
  * parameters.
  * 
  * @param aCAS
  *          the CAS to process
  * 
  * @throws AnalysisEngineProcessException
  *           if a failure occurs during processing.
  * 
  * @see CasAnnotator_ImplBase#process(CAS)
  */
 public void process(CAS aCAS) throws AnalysisEngineProcessException {

	    // add "fake" separator to cas text, computes offsets
	    final int[] offsets;
		StringBuilder sb = new StringBuilder();
		try {
			int currPos = 0;
			offsets = new int[((int) (aCAS.getDocumentText().length() * 1.7)) + 100];
			Collection<Token> tokens = JCasUtil.select(aCAS.getJCas(),
					Token.class);

			for (final Token token : tokens) {
				//System.out.println(token.getCoveredText() + "["
				//		+ token.getBegin() + ":" + token.getEnd() + "]");
				sb.append(token.getCoveredText() + TOKEN_SEPARATOR);

				for (int i = 0; i < (token.getEnd() - token.getBegin()); i++) {
					offsets[currPos++] = token.getBegin() + i;
				}
				offsets[currPos++] = token.getEnd();
				offsets[currPos++] = token.getEnd();
			}
		} catch (CASException e) {
			throw new AnalysisEngineProcessException(e);
		}
		final String matchValue = sb.toString();
	 
	 
   // iterate over all concepts one after the other to process them
   for (int i = 0; i < this.regexConcepts.length; i++) {
     // System.out.println(this.regexConcepts[i]);

     // list of all annotation that must be added to the CAS for this
     // concept
     ArrayList<FeatureStructure> annotsToAdd = new ArrayList<FeatureStructure>();

     // get the rules for the current concept
     Rule[] conceptRules = this.regexConcepts[i].getRules();
     boolean foundMatch = false;
     for (int ruleCount = 0; ruleCount < conceptRules.length; ruleCount++) {

       // get the regex pattern for the current rule
       Pattern pattern = conceptRules[ruleCount].getRegexPattern();

       // get the match type where the rule should be processed on
       Type matchType = conceptRules[ruleCount].getMatchType();

       // get match type iterator from the CAS
       FSIterator<?> mtIterator = aCAS.getAnnotationIndex(matchType).iterator();

       ///String matchValue = null;
       AnnotationFS currentAnnot = null;

       // iterate over all match type annotations where the
       // current rule should be processed on
       while (mtIterator.hasNext()) {

         // get next match type annotation
         currentAnnot = (AnnotationFS) mtIterator.next();

         // check filter features, if all conditions are true
         FilterFeature[] filterFeatures = conceptRules[ruleCount].getMatchTypeFilterFeatures();
         boolean passed = true;
         for (int ff = 0; ff < filterFeatures.length; ff++) {
           // get the current filterFeature featurePath value
           String featureValue = filterFeatures[ff].getFeaturePath().getValue(currentAnnot);
           // check if feature value is set
           if (featureValue != null) {
             // create matcher for the current feature value
             Matcher matcher = filterFeatures[ff].getPattern().matcher(featureValue);
             // check matches - use MATCH_COMPLETE
             if (!matcher.matches()) {
               // no match - stop processing
               passed = false;
               break;
             }
           } else {
             // feature value not set - stop processing
             passed = false;
             break;
           }
         }
         // check if the filter feature check passed all conditions
         if (!passed) {
           // conditions for the current annotation not passed, go on
           // with the next
           continue;
         }

         // get the specified feature path value from the current
         // annotation to run the regex on
         ///matchValue = conceptRules[ruleCount].getMatchTypeFeaturePath().getValue(currentAnnot);

         // check matchValue result, if it is null we don't have to match
         // anything and can go on with the next annotation
         if (matchValue == null) {
           continue;
         }

         // try to match the current pattern on the text
         Matcher matcher = pattern.matcher(matchValue);

         // check the match strategy we have for this rule
         // MatchStrategy - MATCH_ALL
         if (conceptRules[ruleCount].getMatchStrategy() == Rule.MATCH_ALL) {
           int pos = 0;
           while (matcher.find(pos)) {
             // we have a match

             // check rule exceptions
             if (!matchRuleExceptions(conceptRules[ruleCount].getExceptions(), aCAS, currentAnnot)) {

               // create annotations and features
               processConceptInstructions(matcher, currentAnnot, matchValue, aCAS,
                   this.regexConcepts[i], ruleCount, annotsToAdd, offsets);

               // set match found
               foundMatch = true;
             }
             // set start match position for the next match to the
             // current end match position
             if (matcher.end() == pos) {
               // Special case: matched the empty string. If at the end of the input, need
               // to break.
               if (pos == matchValue.length()) {
                 break;
               }
               // Otherwise increment search pos so as not to loop.
               ++pos;
             } else {
               // Default case: match was non-empty.
               pos = matcher.end();
             }
           }
         }
         // MatchStrategy - MATCH_COMPLETE
         else if (conceptRules[ruleCount].getMatchStrategy() == Rule.MATCH_COMPLETE) {
           if (matcher.matches()) {
             // we have a match

             // check rule exceptions
             if (!matchRuleExceptions(conceptRules[ruleCount].getExceptions(), aCAS, currentAnnot)) {

               // create annotations and features
               processConceptInstructions(matcher, currentAnnot, matchValue, aCAS,
                   this.regexConcepts[i], ruleCount, annotsToAdd, offsets);

               // set match found
               foundMatch = true;
             }
           }
         }
         // MatchStrategy - MATCH_FIRST
         else if (conceptRules[ruleCount].getMatchStrategy() == Rule.MATCH_FIRST) {
           if (matcher.find()) {
             // we have a match

             // check rule exceptions
             if (!matchRuleExceptions(conceptRules[ruleCount].getExceptions(), aCAS, currentAnnot)) {

               // create annotations and features
               processConceptInstructions(matcher, currentAnnot, matchValue, aCAS,
                   this.regexConcepts[i], ruleCount, annotsToAdd, offsets);

               // set match found
               foundMatch = true;
             }
           }
         }

         // all analysis is done, we can go to the next annotation
       }
       if (foundMatch) {
         // check setting of processAllConceptRules to decide if
         // we go on with the next rule or not
         if (!this.regexConcepts[i].processAllConceptRules()) {
           // we found a match for the current rule and we don't want go
           // on with further rules of this concept
           break;
         }
       }
     }

     // add all created annotations to the CAS index before moving to the
     // next concept
     for (int x = 0; x < annotsToAdd.size(); x++) {
       aCAS.getIndexRepository().addFS(annotsToAdd.get(x));
     }

     // reset last rule exception annotation since we move to the next rule
     // and everything is new
     this.lastRuleExceptionAnnotation = null;
   }
 }

 /**
  * Check if the rule exception match for the current match type annotation.
  * 
  * @param exceptions
  *          current rule exceptions
  * @param aCAS
  *          current CAS
  * @param annot
  *          current match type annotation
  * 
  * @return returns true if the rule exception match
  */
 private boolean matchRuleExceptions(RuleException[] exceptions, CAS aCAS, AnnotationFS annot) {

   // if we have already checked the exceptions for the current match type
   // annotation, return the
   // last result - this can happen in case of MATCH_ALL match strategy
   if (this.lastRuleExceptionAnnotation == annot) {
     return this.lastRuleExceptionMatch;
   }

   // loop over all rule exceptions
   for (int i = 0; i < exceptions.length; i++) {

     // store current match type annotation for performance reason. In case
     // of MATCH_ALL match
     // strategy maybe the matchRuleException() method is called multiple
     // times for the same
     // match type annotations and in that case the result of the rule
     // exception match is exactly
     // the same.
     this.lastRuleExceptionAnnotation = annot;

     // find covering annotation
     AnnotationFS coverFs = findCoverFS(aCAS, annot, exceptions[i].getType());
     // check if covering annotation was found
     if (coverFs != null) {
       // check if the found coverFs annotation match the exception pattern
       if (exceptions[i].matchPattern(coverFs)) {
         this.lastRuleExceptionMatch = true;
         return this.lastRuleExceptionMatch;
       }
     }
   }

   this.lastRuleExceptionMatch = false;
   return false;
 }

 /**
  * Finds the covering annotation of the specified coverFSType for the given annotation.
  * 
  * @param aCAS
  *          a CAS to search in
  * @param annot
  *          current annotation
  * @param coverFsType
  *          covering annotation type to search for
  * 
  * @return returns the covering annotation FS or null if the covering annotation was not found.
  * 
  */
 private AnnotationFS findCoverFS(CAS aCAS, AnnotationFS annot, Type coverFsType) {

   // covering annotation
   AnnotationFS coverFs = null;

   // create a searchFS of the coverFsType with the annot boundaries to
   // search for it.
   FeatureStructure searchFs = aCAS.createAnnotation(coverFsType, annot.getBegin(), aCAS
       .getDocumentText().length());

   // get the coverFSType iterator from the CAS and move it "near" to the
   // position of the created searchFS.
   FSIterator<?> iterator = aCAS.getAnnotationIndex(coverFsType).iterator();
   iterator.moveTo(searchFs);

   // now the iterator can either point directly to the FS we are searching
   // or it points to the next higher FS in the list. So we either have
   // already found the correct one, of we maybe have to move the iterator to
   // the previous position.

   // check if the iterator at the current position is valid
   if (iterator.isValid()) {
     // iterator is valid, so we either have the correct annotation of we
     // have to move to the
     // previous one, lets check the current FS from the iterator
     // get current FS
     coverFs = (AnnotationFS) iterator.get();
     // check if the coverFS covers the current match type annotation
     if ((coverFs.getBegin() <= annot.getBegin()) && (coverFs.getEnd() >= annot.getEnd())) {
       // we found the covering annotation
       return coverFs;
     }
     // current coverFs does not cover the current match type annotation
     // lets try to move iterator to the previous annotation and check
     // again
     iterator.moveToPrevious();
     // check if the iterator is still valid after me move it to the
     // previous FS
     if (iterator.isValid()) {
       // get FS
       coverFs = (AnnotationFS) iterator.get();
       // check the found coverFS covers the current match type
       // annotation
       if ((coverFs.getBegin() <= annot.getBegin()) && (coverFs.getEnd() >= annot.getEnd())) {
         // we found the covering annotation
         return coverFs;
       }
     }
   }
   // iterator is invalid lets try to move the iterator to the last FS and
   // check the FS
   iterator.moveToLast();
   // check if the iterator is valid after we move it
   if (iterator.isValid()) {
     // get FS
     coverFs = (AnnotationFS) iterator.get();
     // check the found coverFS covers the current match type annotation
     if ((coverFs.getBegin() <= annot.getBegin()) && (coverFs.getEnd() >= annot.getEnd())) {
       // we found the covering annotation
       return coverFs;
     }
   }
   // no covering annotation found
   return null;
 }

 /**
  * The createAnnotations method creates the annotations and features for the given rule matches.
  * 
  * @param matcher
  *          current regex matcher
  * @param annot
  *          match type annotation
  * @param matchingText
  *          text that is used to match
  * @param aCAS
  *          current CAS
  * @param concept
  *          current concept
  * @param ruleIndex
  *          current rule index
  * @param annotsToAdd
  *          array for the annotations that should be created
  */
 private void processConceptInstructions(Matcher matcher, AnnotationFS annot, String matchingText,
     CAS aCAS, Concept concept, int ruleIndex, ArrayList<FeatureStructure> annotsToAdd, int[] offsets)
     throws RegexAnnotatorProcessException {

   // create local annotation map for reference features
   HashMap<String, FeatureStructure> annotMap = new HashMap<String, FeatureStructure>();

   // has the rule some reference features to set
   boolean hasReferenceFeatures = false;

   // get annotations that should be created
   Annotation[] annotations = concept.getAnnotations();
   for (int a = 0; a < annotations.length; a++) {
     // get annotation type
     Type annotType = annotations[a].getAnnotationType();

     // get local start and end position of the match in the matchingText
     int localStart = annotations[a].getBegin().getMatchPosition(matcher);
     int localEnd = annotations[a].getEnd().getMatchPosition(matcher);

     // check if match group positions are valid. If they are invalid,
     // the match group is available but has no matching content
     if (localStart == -1 || localEnd == -1) {
       // match group positions are invalid, so we cannot create the
       // annotation
       continue;
     }
     
 	// ren: translate to offsets
	localStart = offsets[localStart];
	localEnd = offsets[localEnd];

     // set default validation value to true, by default all annotations
     // are created
     boolean validation = true;

     // check if an validator for the current annotation is available
     if (annotations[a].hasValidator()) {
       // get the substring of the match from the matching text
       String matchText = matchingText.substring(localStart, localEnd);
       // validate annotation
       try {
         validation = annotations[a].validate(matchText, concept.getRules()[ruleIndex].getId());
       } catch (Exception ex) {
         throw new RegexAnnotatorProcessException("regex_annotator_error_validating_annotation",
             new Object[] { annotations[a].getId(), matchText, Integer.valueOf(localStart),
                 Integer.valueOf(localEnd) }, ex);
       }
     }

     // only create annotation if the validation was true
     if (validation == true) {
       // create annotation start and begin positions dependent of the rule
       // matching
       if (concept.getRules()[ruleIndex].isFeaturePathMatch()) {
         // we match a feature path, use a source annotation boundaries
         // for the annotation that is created
         localStart = annot.getBegin();
         localEnd = annot.getEnd();
       } else {
         // we match no feature path, make positions absolute to the
         // document text -> add match type annotation offset.
         localStart = annot.getBegin() + localStart;
         localEnd = annot.getBegin() + localEnd;
       }
       // create annotation for this match
       FeatureStructure fs = aCAS.createAnnotation(annotType, localStart, localEnd);

       // get features for the current annotation
       Feature[] features = annotations[a].getFeatures();
       for (int f = 0; f < features.length; f++) {
         // get feature type
         int type = features[f].getType();

         // check if we have a reference feature or not
         if (type == Feature.FLOAT_FEATURE || type == Feature.INTEGER_FEATURE
             || type == Feature.STRING_FEATURE) {
           // we have no reference feature
           // replace match groups in the feature value
           String featureValue = replaceMatchGroupValues(features[f].getValue(), matcher, concept
               .getRules()[ruleIndex]);

           // do featureValue normalization
           try {
             // try to set the normalized feature value, if no
             // normalization was specified for the feature, the
             // original feature value is set
             if (featureValue != null) {
               featureValue = features[f].normalize(featureValue, concept.getRules()[ruleIndex]
                   .getId());
             }
           } catch (Exception ex) {
             throw new RegexAnnotatorProcessException(
                 "regex_annotator_error_normalizing_feature_value", new Object[] { featureValue,
                     features[f].getName() }, ex);
           }

           // set feature value at the annotation in dependence of the
           // feature type
           if (type == Feature.FLOAT_FEATURE) {
             try {
               if (featureValue != null) {
                 Number number = this.floatNumberFormat.parse(featureValue);
                 fs.setFloatValue(features[f].getFeature(), number.floatValue());
               }
             } catch (ParseException ex) {
               this.logger.logrb(Level.WARNING, "RegExAnnotator", "processConceptInstructions",
                   MESSAGE_DIGEST, "regex_annotator_warning_number_format_conversion",
                   new Object[] { featureValue, features[f].getFeature().getName(), "float" });
             }

           } else if (type == Feature.INTEGER_FEATURE) {
             try {
               if (featureValue != null) {
                 Number number = this.integerNumberFormat.parse(featureValue);
                 fs.setIntValue(features[f].getFeature(), number.intValue());
               }
             } catch (ParseException ex) {
               this.logger.logrb(Level.WARNING, "RegExAnnotator", "processConceptInstructions",
                   MESSAGE_DIGEST, "regex_annotator_warning_number_format_conversion",
                   new Object[] { featureValue, features[f].getFeature().getName(), "integer" });
             }
           } else if (type == Feature.STRING_FEATURE) {
             fs.setStringValue(features[f].getFeature(), featureValue);
           }
         } else if (type == Feature.REFERENCE_FEATURE) {
           // we have a reference feature, we have to set this later
           // since we cannot be sure that the referenced annotation is
           // already created
           hasReferenceFeatures = true;
         } else if (type == Feature.RULEID_FEATURE) {
           // get rule id and set it as feature value
           String ruleId = concept.getRules()[ruleIndex].getId();
           fs.setStringValue(features[f].getFeature(), ruleId);
         } else if (type == Feature.CONFIDENCE_FEATURE) {
           // get rule confidence value and set it as feature value
           float confidence = concept.getRules()[ruleIndex].getConfidence();
           fs.setFloatValue(features[f].getFeature(), confidence);
         }
       }

       // add annotation to the local HashMap that is used to set
       // annotation
       // reference features, the annotation must only be added in case
       // that
       // an annotation id was specified.
       if (annotations[a].getId() != null) {
         annotMap.put(annotations[a].getId(), fs);
       }

       // add annotation to the list of feature structures that must be
       // added
       // to the index
       annotsToAdd.add(fs);
     }

   } // end of annotation processing

   // if we detected previously some reference feature types we have to set
   // them now
   if (hasReferenceFeatures) {
     // iterate again over the annotation array
     for (int a = 0; a < annotations.length; a++) {
       // get all features for the current annotation
       Feature[] features = annotations[a].getFeatures();
       for (int f = 0; f < features.length; f++) {
         // get feature type
         int type = features[f].getType();

         // check if we have a reference feature, we are only
         // interested in reference features now
         if (type == Feature.REFERENCE_FEATURE) {
           // search for the annotation the feature belongs to, the
           // annotation was created earlier
           // to search for the correct annotation we use the current
           // annotation ID
           FeatureStructure fs = annotMap.get(annotations[a].getId());

           // search for the referenced annotation ID
           // the annotation ID we search for is specified in the feature
           // value
           FeatureStructure refFs = annotMap.get(features[f].getValue());

           // set reference feature value
           fs.setFeatureValue(features[f].getFeature(), refFs);
         }
       }
     }
   } // end - set reference feature value

   // process update features of the current match type annotation
   // get all match type update features of the current rule
   Feature[] updateFeatures = concept.getRules()[ruleIndex].getMatchTypeUpdateFeatures();
   for (int f = 0; f < updateFeatures.length; f++) {

     int type = updateFeatures[f].getType();
     // check if we have a reference feature or not
     if (type == Feature.FLOAT_FEATURE || type == Feature.INTEGER_FEATURE
         || type == Feature.STRING_FEATURE) {
       // we have no reference feature
       // replace match groups in the feature value
       String featureValue = replaceMatchGroupValues(updateFeatures[f].getValue(), matcher,
           concept.getRules()[ruleIndex]);

       // do featureValue normalization
       try {
         // try to set the normalized feature value, if no
         // normalization was specified for the feature, the
         // original feature value is set
         featureValue = updateFeatures[f].normalize(featureValue, concept.getRules()[ruleIndex]
             .getId());
       } catch (Exception ex) {
         throw new RegexAnnotatorProcessException(
             "regex_annotator_error_normalizing_feature_value", new Object[] { featureValue,
                 updateFeatures[f].getName() }, ex);
       }

       // set feature value at the annotation in dependence of the feature
       // type
       if (type == Feature.FLOAT_FEATURE) {
         annot.setFloatValue(updateFeatures[f].getFeature(), Float.parseFloat(featureValue));
       } else if (type == Feature.INTEGER_FEATURE) {
         annot.setIntValue(updateFeatures[f].getFeature(), Integer.parseInt(featureValue));
       } else if (type == Feature.STRING_FEATURE) {
         annot.setStringValue(updateFeatures[f].getFeature(), featureValue);
       }
     } else if (type == Feature.REFERENCE_FEATURE) {
       // search for the referenced annotation ID
       // the annotation ID we search for is specified in the feature value
       FeatureStructure refFs = annotMap.get(updateFeatures[f].getValue());

       // set reference feature value
       annot.setFeatureValue(updateFeatures[f].getFeature(), refFs);

     } else if (type == Feature.RULEID_FEATURE) {
       // get rule id and set it as feature value
       String ruleId = concept.getRules()[ruleIndex].getId();
       annot.setStringValue(updateFeatures[f].getFeature(), ruleId);
     } else if (type == Feature.CONFIDENCE_FEATURE) {
       // get rule confidence value and set it as feature value
       float confidence = concept.getRules()[ruleIndex].getConfidence();
       annot.setFloatValue(updateFeatures[f].getFeature(), confidence);
     }

   }

 }

 /**
  * replace the string containing match group syntax with the current match group values.
  * 
  * @param featureValue
  *          string value that contains the match group syntax
  * 
  * @param matcher
  *          regex matcher to match the match groups
  * 
  * @return returns the replaced match group value content
  */
 private String replaceMatchGroupValues(String featureValue, Matcher matcher, Rule rule)
     throws RegexAnnotatorProcessException {
   StringBuffer replaced = new StringBuffer();
   int pos = 0;
   int end = featureValue.length();
   char c;
   // Iterate over the input text to find the match groups that must be
   // replaced.
   // In the input text, all $ and \ characters must be escaped by \.
   while (pos < end) {
     c = featureValue.charAt(pos);
     // Everything followed by a \ was escaped and the \ (escape character)
     // can be removed now
     if (c == '\\') {
       // skip escape character
       ++pos;

       // add escaped character to the output
       if (pos < end) {
         replaced.append(featureValue.charAt(pos));
         // go to the next character
         ++pos;
       }
     } else if (c == '$') {
       // this must be a match group $n since all other $ characters must
       // be escaped with a \ which is handled above.
       // skip $ character we are only interested in the match group number
       // or name
       // match group name syntax is ${match group name}
       ++pos;
       if (pos < end) {
         // get next char to check if we have a match group number or a
         // match group name
         c = featureValue.charAt(pos);

         int groupNumber = -1;
         if (c == '{') {
           // we have a match group name
           // skip grace '{'
           ++pos;
           // get match group name
           int matchNameEnd = featureValue.indexOf("}", pos);
           if (matchNameEnd > -1) {
             String matchGroupName = featureValue.substring(pos, matchNameEnd);
             // get match group number for the given match group name
             groupNumber = rule.getMatchGroupNumber(matchGroupName);
             if (groupNumber == -1) {
               throw new RegexAnnotatorProcessException(
                   "regex_annotator_error_match_group_name_not_found", new Object[] {
                       matchGroupName, rule.getId() });
             }
             // set pos to the end of the match group name syntax
             pos = matchNameEnd + 1;
           }
         } else {
           // we have a match group number
           // convert match group number to integer value
           groupNumber = c - '0';
           // skip match group number
           ++pos;
         }

         // get match group content
         String groupMatch = matcher.group(groupNumber);
         // add match group content to the output
         if (groupMatch != null) {
           replaced.append(groupMatch);
         }
       }
     } else {
       // default output character that is added to the output
       replaced.append(c);
       ++pos;
     }
   }
   return replaced.toString();
 }

 /**
  * Helper class to bundle the XML Concept file name and the concept file input stream to one
  * object.
  */
 private static class ConceptFile {
   // concept file path name
   private String filePath;

   // concept file stream
   private InputStream stream;

   /**
    * creates a new conceptFile object with the file path and the stream
    * 
    * @param filePath
    *          concept file path
    * 
    * @param stream
    *          concept file stream
    */
   public ConceptFile(String filePath, InputStream stream) {
     this.filePath = filePath;
     this.stream = stream;
   }

   /**
    * Returns the concept file path name
    * 
    * @return concept file path name
    */
   public String getFilePath() {
     return this.filePath;
   }

   /**
    * Returns the concept file stream
    * 
    * @return concept file stream
    */
   public InputStream getStream() {
     return this.stream;
	}
    }
}
