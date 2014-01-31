package w.ch.epfl.bbp.nlp.br.normalize;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ubic.pubmedgate.resolve.mentionEditors.BracketRemoverMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.CytoPrefixMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.DirectionRemoverMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.DirectionSplittingMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.HemisphereStripMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.LowerTrimMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.MentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.NDotExpanderMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.NucleusOfTheRemoverMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.OfTheRemoverMentionEditor;
import ubic.pubmedgate.resolve.mentionEditors.RegionSuffixRemover;

/**
 * Modifiers from French 2011
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Normalize {

    List<MentionEditor> editors = new LinkedList<MentionEditor>();

    public Normalize() throws Exception {
        editors.add(new LowerTrimMentionEditor());
        editors.add(new DirectionSplittingMentionEditor());
        editors.add(new HemisphereStripMentionEditor());
        editors.add(new BracketRemoverMentionEditor());
        editors.add(new NDotExpanderMentionEditor());
        editors.add(new OfTheRemoverMentionEditor());
        editors.add(new CytoPrefixMentionEditor());
        editors.add(new RegionSuffixRemover());
        editors.add(new DirectionRemoverMentionEditor());
        editors.add(new NucleusOfTheRemoverMentionEditor());
        editors.add(new DirectionRemoverMentionEditor());
    }

    /**
     * Run the input string through all of the mention editors to clean and edit
     * it
     */
    public Set<String> processMentionString(String mention) {
        Set<String> result = new HashSet<String>();
        result.add(mention);
        for (MentionEditor editor : editors) {
            Set<String> newResult = new HashSet<String>();
            for (String mentionEdit : result) {
                newResult.addAll(editor.editMention(mentionEdit));
            }
            result.addAll(newResult);
        }
        return result;
    }

    // public Model resolve(Set<Resource> mentions) {
    // Model model = ModelFactory.createDefaultModel();
    // for (Resource mention : mentions) {
    // model = model.union(resolve(mention));
    // }
    // return model;
    // }
}
