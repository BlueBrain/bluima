#!/bin/bash -l

#SBATCH --job-name="Mesh Matrix"
#SBATCH --nodes=1
#SBATCH --ntasks=2
#SBATCH --ntasks-per-node=2
#SBATCH --mem=8096
#SBATCH --time=10:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-mesh-matrix-200-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-mesh-matrix-200-stderr.log

krenew -b -K 30

cd $HOME/private/topic_models

java -Xmx8000M  -Dblue_uima_home=$HOME/private/blue_uima_trunk -jar $HOME/private/software/sbt/sbt-launch-0.12.0.jar "run-main ch.epfl.bbp.uima.topicmodels.exploitation.MeshCorrelation"

