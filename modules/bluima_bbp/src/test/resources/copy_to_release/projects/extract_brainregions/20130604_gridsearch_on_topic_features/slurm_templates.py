# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()


#A: top1 & staged
for i in range(1, 3): # topScenario = [1,2]
  for j in range(0, 10):
    output_name = 'output_templates/slurmA{0}_{1}.sh' .format(i,j)
    print 'sbatch %s' % output_name
    template('template.sh', output_name, {
      'job_name': 'brA_{0}_{1}' .format(i,j),
      'comb' : 1, #whatever
      'm' : i 
    })  

#B: topNTopics and minProb 
for i in range(0, 56): 
  for j in range(0, 10):
    output_name = 'output_templates/slurmB{0}_{1}.sh' .format(i,j)
    print 'sbatch %s' % output_name
    template('template.sh', output_name, {
      'job_name': 'brB_{0}_{1}' .format(i,j),
      'comb' : i,
      'topScenario' : 3 # fixed here
    })	
