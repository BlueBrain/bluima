#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --nodes=1
#SBATCH --ntasks=2
#SBATCH --ntasks-per-node=2
#SBATCH --mem=1700
#SBATCH --time=1:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20131205_prot/%(job_name)s-out.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20131205_prot/%(job_name)s-err.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_name)s: started"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20131205

./bin/run_pipeline \
pipelines/projects/extract_proteinconc/20131205_slurm/20131205_slurm.pipeline \
/nfs4/bbp.epfl.ch/simulation/nlp/data/20131205_prot/ \
%(job_name)s %(from)s %(to)s
