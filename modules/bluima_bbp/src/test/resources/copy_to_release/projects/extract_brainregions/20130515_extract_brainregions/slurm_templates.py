# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()

# max 23 435 706

for i in range(0,90):
  output_name = 'output_templates/slurm%i.sh' % i
  print 'sbatch %s' % output_name
  template('template.sh', output_name, {
    'job_name': 'br%i' % i, 
    'from_id' : (i * 250000),
    'to_id'   : (i * 250000 + 249999)
  })	
