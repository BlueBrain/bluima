#!/bin/bash -l
#SBATCH --job-name="nn7_%(job_id)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=4192
#SBATCH --time=01:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/nn_20150610/%(job_id)s-stdout.log
#SBATCH  --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/nn_20150610/%(job_id)s-sterr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_id)s: started"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20150610

./bin/run_pipeline pipelines/projects/extract_neurons/20150610_7th_scaleout/20150610_neuroNER_scale.pipeline \
%(job_id)s %(from_id)s %(to_id)s
