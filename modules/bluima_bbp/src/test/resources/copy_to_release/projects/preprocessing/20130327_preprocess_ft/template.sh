#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --nodes=1
#SBATCH --ntasks=2
#SBATCH --ntasks-per-node=2
#SBATCH --mem=3500
#SBATCH --time=14:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/simulation/nlp/log/slurm/out/%(job_name)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/simulation/nlp/log/slurm/out/%(job_name)s-stderr.log

krenew -b -K 30

export LANG=en_US.UTF-8

echo "%(job_name)s: started with %(from_id)s %(to_id)s"

cd /nfs4/bbp.epfl.ch/simulation/nlp/soft/bluima/release_20130327

./bin/run_pipeline \
pipelines/projects/preprocessing/20130327_preprocess_ft/20130327_preprocess_ft.pipeline \
%(from_id)s %(to_id)s
