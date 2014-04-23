# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()



for ner in ['bams', 'aba', 'brainer']:

  for i in range(0,2067):
    output_name = 'ftns_output_templates/{0}_slurm{1}.sh'.format(ner, i)
    print 'sbatch %s' % output_name
    template('ftns_template.sh', output_name, {
      'job_id': '%i' % i,
      'ner': ner
    })
