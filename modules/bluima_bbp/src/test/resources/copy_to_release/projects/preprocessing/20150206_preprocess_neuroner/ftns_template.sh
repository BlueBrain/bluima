#!/bin/bash -l
#SBATCH --job-name="%(job_id)sft"
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --ntasks-per-node=1
#SBATCH --mem=2048
#SBATCH --time=0:30:00
#SBATCH --partition=b3h
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/201502/ft_%(job_id)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/201502/ft_%(job_id)s-sterr.log

echo "%(job_id)s --: started" > /nfs4/bbp.epfl.ch/user/richarde/slurm_logs/201502/ft_%(job_id)s-stdout.log



krenew -b -K 30
export LANG=en_US.UTF-8

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20141006

./bin/run_pipeline pipelines/projects/preprocessing/20150206_preprocess_neuroner/ftns.pipeline %(job_id)s


