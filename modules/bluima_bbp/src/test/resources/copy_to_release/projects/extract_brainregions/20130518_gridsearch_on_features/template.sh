#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=3500
#SBATCH --time=15:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_out2/%(job_name)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_out2/%(job_name)s-stderr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_name)s: started with comb %(comb)s"

cd /nfs4/bbp.epfl.ch/simulation/nlp/soft/bluima/release_20130518

./bin/run_pipeline pipelines/projects/extract_brainregions/20130518_gridsearch_on_features/20130518_gridsearch_on_features.pipeline %(comb)s
