#!/bin/bash -l

#SBATCH --job-name="Preprocess Pubmed"
#SBATCH --nodes=1
#SBATCH --ntasks=2
#SBATCH --ntasks-per-node=2
#SBATCH --mem=5096
#SBATCH --time=1-10:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-pubmed-pre-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-pubmed-pre-stderr.log

krenew -b -K 30

cd $HOME/private/topic_models

java -Xmx5000M  -Dblue_uima_home=$HOME/private/blue_uima_trunk -jar $HOME/private/software/sbt/sbt-launch-0.12.0.jar "run-main ch.epfl.bbp.uima.topicmodels.preprocessing.PubmedPreprocessor"
