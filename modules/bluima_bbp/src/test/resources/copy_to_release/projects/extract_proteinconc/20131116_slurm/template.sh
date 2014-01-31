#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --exclude=bbplxviz03,bbplxviz04
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=3500
#SBATCH --time=2:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20131117_prot/%(job_name)s-out.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20131117_prot/%(job_name)s-err.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_name)s: started"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20131117

./bin/run_pipeline \
pipelines/projects/extract_proteinconc/20131116_slurm/20131116_slurm.pipeline \
/nfs4/bbp.epfl.ch/simulation/nlp/data/20131117_prot/ \
%(job_name)s
