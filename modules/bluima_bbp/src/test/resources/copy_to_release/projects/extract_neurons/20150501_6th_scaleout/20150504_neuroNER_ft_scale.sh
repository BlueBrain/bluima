#!/bin/bash -l
#SBATCH --job-name="nft%(job_id)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=4192
#SBATCH --time=01:00:00
#SBATCH --partition=batch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20150504/%(job_id)s-stdout.log
#SBATCH  --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20150504/%(job_id)s-sterr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_id)s: started"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20150501

./bin/run_pipeline pipelines/projects/extract_neurons/20150501_6th_scaleout/20150504_neuroNER_ft_scale.pipeline \
%(job_id)s
