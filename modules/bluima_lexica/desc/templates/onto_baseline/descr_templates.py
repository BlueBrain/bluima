# script to generate config files for UIMA ConceptMapper
 
def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()

date = '_20120830.xml'
end = 'ConceptMapper.xml'

for ontology in ['verb', 'region', 'cell', 'protein', 'disease']:
	
    Ont = ontology.capitalize()

    template('../baseline/template.xml', './../../onto_baseline/' + ontology + end, {
      'name': Ont,
      'FeatureList': '<string>DictCanon</string>',
      'ResultingAnnotationName': 'ch.epfl.bbp.uima.types.' + Ont + 'DictTerm',
      'dict_file': 'resources/onto_baseline/' + ontology + date,
    })

    print '<rule>\n    <pattern>ch.epfl.bbp.uima.types.%sDictTerm</pattern>\n    <label>%s</label>\n    <style>color:black; background:cyan</style>\n</rule>\n' % (Ont, Ont)
