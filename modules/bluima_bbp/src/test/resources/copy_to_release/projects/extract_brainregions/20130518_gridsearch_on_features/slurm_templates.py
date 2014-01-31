# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()

# max 23 435 706

for i in range(0, 96): 
  for j in range(0, 4):
    output_name = 'output_templates/slurm{0}_{1}.sh' .format(i,j)
    print 'sbatch %s' % output_name
    template('template.sh', output_name, {
      'job_name': 'brr_{0}_{1}' .format(i,j),
      'comb' : i
    })	
