# script to generate config files for Slurm on viz-cluster, using a template

def template(template_file, out_file,vars):
  out_f = file(out_file, 'w')
  out_f.write(open(template_file, 'r').read() % vars)
  out_f.close()


##23420398 / 250  = 93682
# 23420398 / 2500 = 9369
# 23420398 / 5000 = 4685 <---

for i in range(0, 4685):
  output_name = 'output_templates/slurm_%s.sh' % i
  print 'sbatch %s' % output_name
  template('20150610_neuroNER_scale.sh', output_name, {
    'job_id': '%i' % i,
    'from_id' : (i * 5000),
    'to_id'   : (i * 5000 + 4999)
  })
