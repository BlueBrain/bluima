#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --exclude=bbplxviz06
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=3500
#SBATCH --time=2:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_out6/%(job_name)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_out6/%(job_name)s-stderr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_name)s: started"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/release_20131003

./bin/run_pipeline pipelines/projects/extract_IC/20130930_slurm/20130930_extract_ic_x_subcell.pipeline %(job_name)s

