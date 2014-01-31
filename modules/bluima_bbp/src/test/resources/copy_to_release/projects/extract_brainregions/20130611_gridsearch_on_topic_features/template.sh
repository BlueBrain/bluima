#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --nodes=1
#SBATCH --ntasks=4
#SBATCH --ntasks-per-node=4
#SBATCH --mem=5900
#SBATCH --time=15:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_out4/%(job_name)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_out4/%(job_name)s-stderr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_name)s: started with topNTopics %(topNTopics)s"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/release_20130611

./bin/run_pipeline pipelines/projects/extract_brainregions/20130611_gridsearch_on_topic_features/20130611_gridsearch_on_features.pipeline %(topNTopics)s
