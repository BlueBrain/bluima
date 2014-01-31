#!/bin/bash -l
#SBATCH --job-name="1"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=3500
#SBATCH --time=2:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_out5/1-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_out5/1-stderr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "1: started"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/release_20130704

./bin/run_pipeline pipelines/projects/extract_brainregions/20130703_extract_BAMS/ 1
