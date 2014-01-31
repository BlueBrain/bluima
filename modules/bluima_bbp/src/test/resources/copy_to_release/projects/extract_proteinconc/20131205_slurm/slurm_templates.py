# script to generate config files for Slurm on viz-cluster, using a template
# usage:
# >python slurm_templates.py > start.sh

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()

print '#!/bin/bash\n'

for i in range(0, 1000):
  output_name = 'output_templates/slurm{0}.sh' .format(i)
  print 'sbatch %s' % output_name
  template('template.sh', output_name, {
    'job_name': i,
    'from':    (i * 24000 + 1),
    'to':      (i * 24000 + 24000)
  })

print '\necho done\n'
