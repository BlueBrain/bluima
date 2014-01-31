#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --nodes=1
#SBATCH --ntasks=2
#SBATCH --ntasks-per-node=2
#SBATCH --mem=7192
#SBATCH --time=46:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_out/%(job_name)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_out/%(job_name)s-stderr.log
krenew -b -K 30
export LANG=en_US.UTF-8
echo "%(job_name)s: started with %(from_id)s %(to_id)s"
cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/release_20130214/
./bin/run_pipeline pipelines/projects/dca/preprocess_pubmed3.pipeline %(from_id)s %(to_id)s %(job_name)s
echo "%(job_name)s: done with %(from_id)s %(to_id)s"
