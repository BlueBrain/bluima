#!/bin/bash
#
#SBATCH --job-name="testjob"
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --partition=batch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/201502/test.log
#SBATCH  --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/201502/test.log
#SBATCH --time=1:30:00
#SBATCH --mem=512

echo "my job started!"
echo "writing to file" > /nfs4/bbp.epfl.ch/user/richarde/slurm_logs/201502/test_output_min.txt
