# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()


for i in range(0, 10): # topics 

  for j in range(0, 10): # repetitions
  
    output_name = 'output_templates/slurmC{0}_{1}.sh' .format(i,j)
    print 'sbatch %s' % output_name
    template('template.sh', output_name, {
      'job_name': 'brC_{0}_{1}' .format(i,j),
      'topNTopics' : i
    })
