# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()

for i in range(0,891):
  output_name = 'output_templates/slurm%i.sh' % i
  print 'sbatch %s' % output_name
  template('template.sh', output_name, {
    'job_name': '2dca%i' % i, 
    'from_id' : (i * 25000),
    'to_id'   : (i * 25000 + 24999)
  })	
