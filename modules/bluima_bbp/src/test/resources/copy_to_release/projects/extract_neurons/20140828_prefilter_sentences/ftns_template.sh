#!/bin/bash -l
#SBATCH --job-name="sent%(job_id)s"
#SBATCH --nodes=1
#SBATCH --ntasks=3
#SBATCH --ntasks-per-node=3
#SBATCH --mem=4192
#SBATCH --time=6:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@gmail.com
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20140828_prefilter_sentences/ft_%(job_id)s-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20140828_prefilter_sentences/ft_%(job_id)s-sterr.log

krenew -b -K 30
export LANG=en_US.UTF-8

echo "%(job_id)s: started"

cd /nfs4/bbp.epfl.ch/user/richarde/dev/bluebrain/bluima_20140905

./bin/run_pipeline pipelines/projects/extract_neurons/20140828_prefilter_sentences/ftns.pipeline \
/nfs4/bbp.epfl.ch/simulation/nlp/data/20140828_prefilter_sentences \
%(job_id)s




