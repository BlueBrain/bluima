#!/bin/bash -l
#SBATCH --job-name="nn9_%(job_id)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=4192
#SBATCH --time=02:00:00
#SBATCH --partition=test
#SBATCH --account=proj31
#SBATCH --output=/gpfs/bbp.cscs.ch/home/richarde/slurm_logs/20150828_neuroner_9th_scaleout/%(job_id)s-stdout.log
#SBATCH  --error=/gpfs/bbp.cscs.ch/home/richarde/slurm_logs/20150828_neuroner_9th_scaleout/%(job_id)s-sterr.log

export LANG=en_US.UTF-8

echo "%(job_id)s: started"

cd /gpfs/bbp.cscs.ch/home/richarde/dev/bluima_20150902/

./bin/run_pipeline pipelines/projects/extract_neurons/20150828_9th_scaleout/20150828_neuroNER_scale.pipeline \
%(job_id)s %(from_id)s %(to_id)s
