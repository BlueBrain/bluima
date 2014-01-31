#!/bin/bash -l
#SBATCH --job-name="%(job_name)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=4192
#SBATCH --time=46:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_out/%(job_name)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_out/%(job_name)s-stderr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_name)s: started with %(from_id)s %(to_id)s"

cd /nfs4/bbp.epfl.ch/simulation/nlp/soft/bluima/release_20130515

./bin/run_pipeline pipelines/projects/extract_brainregions/20130515_extract_brainregions/20130515_extract_brainregions.pipeline %(from_id)s %(to_id)s /nfs4/bbp.epfl.ch/simulation/nlp/data/extracted_brainregions/20130515_malletNerOnPubmedAbstracts/%(job_name)s


