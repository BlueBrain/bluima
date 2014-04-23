# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()

# max 24 511 642

for ner in ['bams', 'aba', 'brainer']:

  for i in range(0,491):
    output_name = 'abstracts_output_templates/{0}_slurm{1}.sh'.format(ner, i)
    print 'sbatch %s' % output_name
    template('abstracts_template.sh', output_name, {
      'job_id': '%i' % i,
      'ner': ner,
      'from_id' : (i * 50000),
      'to_id'   : (i * 50000 + 49999)
    })

