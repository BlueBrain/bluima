#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=3500
#SBATCH --time=2:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/20131024_out_ic/%(job_name)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/20131024_out_ic/%(job_name)s-stderr.log

krenew -b -K 30

export LANG=en_US.UTF-8

echo "%(job_name)s: started with %(from_id)s %(to_id)s"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20131024/

# args: output path, jobid, from, to
./bin/run_pipeline \
pipelines/projects/extract_IC/20131021_slurm/20131021_extract_ic_x_subcell.pipeline \
/nfs4/bbp.epfl.ch/simulation/nlp/data/20131024_ic-X-subcell/ \
%(job_name)s %(from_id)s %(to_id)s
