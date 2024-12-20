#!/bin/bash -l
#SBATCH --job-name="nn3%(job_id)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=4192
#SBATCH --time=01:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20141006_neuroner_3nd_scaleout/%(job_id)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20141006_neuroner_3nd_scaleout/%(job_id)s-sterr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_id)s: started"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20141006

./bin/run_pipeline pipelines/projects/extract_neurons/20141006_neuroner_3nd_scaleout.pipeline \
#/nfs4/bbp.epfl.ch/simulation/nlp/data/20141006_neuroner_3nd_scaleout/%(job_id)s \
target/test \
%(from_id)s %(to_id)s
