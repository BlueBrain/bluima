# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()



##23420398 / 250  = 93682
# 23420398 / 2500 = 9369

for i in range(0, 9369):
  output_name = '3rd_scaleout/slurm_%s.sh' % i
  print 'sbatch %s' % output_name
  template('20140915_neuroner_3nd_scaleout.sh', output_name, {
    'job_id': '%i' % i,
    'from_id' : (i * 2500),
    'to_id'   : (i * 2500 + 2499)
  })
