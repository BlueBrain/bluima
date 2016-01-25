#!/bin/bash -l
#SBATCH --job-name="nn9_0"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=4192
#SBATCH --time=02:00:00
#SBATCH --partition=test
#SBATCH --account=proj31
#SBATCH --output=/gpfs/bbp.cscs.ch/home/richarde/slurm_logs/20150828_neuroner_9th_scaleout/0-stdout.log
#SBATCH  --error=/gpfs/bbp.cscs.ch/home/richarde/slurm_logs/20150828_neuroner_9th_scaleout/0-sterr.log

export LANG=en_US.UTF-8

echo "0: started"

cd /gpfs/bbp.cscs.ch/home/richarde/dev/bluima_20150902/

./bin/run_pipeline pipelines/projects/extract_neurons/20150828_9th_scaleout/20150828_neuroNER_scale.pipeline \
0 0 4999
