#!/bin/bash -l
#SBATCH --job-name="preproc"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=6000
#SBATCH --time=3-00:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20140414.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20140414.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "job started"


cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20140414/

./bin/run_pipeline pipelines/projects/preprocessing/20131014_preprocess_corpus_to_lda-c/20140414_100k_ns.pipeline

