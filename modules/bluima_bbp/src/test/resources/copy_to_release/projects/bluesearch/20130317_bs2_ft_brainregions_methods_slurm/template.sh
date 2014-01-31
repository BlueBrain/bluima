#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --nodes=1
#SBATCH --ntasks=2
#SBATCH --ntasks-per-node=2
#SBATCH --mem=5200
#SBATCH --time=10:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_out/bs2/%(job_name)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_out/bs2/%(job_name)s-stderr.log

krenew -b -K 30

export LANG=en_US.UTF-8

echo "%(job_name)s: started with %(from_id)s %(to_id)s"

mkdir /nfs4/bbp.epfl.ch/simulation/nlp/data/bluesearch/bs2/%(job_name)s

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/release_20130313/

./bin/run_pipeline pipelines/projects/bluesearch/20130317_bs2_ft_brainregions_methods_slurm/20130317_bs2_ft_brainregions_methods_slurm.pipeline \
 %(from_id)s %(to_id)s /nfs4/bbp.epfl.ch/simulation/nlp/data/bluesearch/bs2/%(job_name)s
