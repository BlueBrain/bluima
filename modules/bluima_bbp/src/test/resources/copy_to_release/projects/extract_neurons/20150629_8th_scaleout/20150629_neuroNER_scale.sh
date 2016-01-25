#!/bin/bash -l
#SBATCH --job-name="nn8_%(job_id)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=4192
#SBATCH --time=02:00:00
#SBATCH --partition=test
#SBATCH --account=proj31
#SBATCH --output=/gpfs/bbp.cscs.ch/home/richarde/slurm_logs/20150629_neuroner_8th_scaleout/%(job_id)s-stdout.log
#SBATCH  --error=/gpfs/bbp.cscs.ch/home/richarde/slurm_logs/20150629_neuroner_8th_scaleout/%(job_id)s-sterr.log

export LANG=en_US.UTF-8

echo "%(job_id)s: started"

cd /gpfs/bbp.cscs.ch/home/richarde/dev/bluima_20150629/

./bin/run_pipeline pipelines/projects/extract_neurons/20150629_8th_scaleout/20150629_neuroNER_scale.pipeline \
%(job_id)s %(from_id)s %(to_id)s
