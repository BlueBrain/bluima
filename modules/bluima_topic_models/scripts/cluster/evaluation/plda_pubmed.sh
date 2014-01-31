#!/bin/bash -l
#SBATCH --job-name="PLDA Pubmed"
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --ntasks-per-node=1
#SBATCH --mem=4096
#SBATCH --time=30:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-plda-pubmed-noopt-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-plda-pubmed-noopt-stderr.log
 
# In case there are per-group custom initialization files
#. /nfs4/bbp.epfl.ch/group/visualization/module/modules.bash
# Load your required module files here
#module load MODULE_TO_BE_LOADED
 
# To avoid Kerberos tickets becoming expired, run the following in
# the background to check every 30min and try to renew it
krenew -b -K 30
 
echo "On which node your job has been scheduled :"
echo $SLURM_JOB_NODELIST
echo "Print current shell limits :"
ulimit -a

./plda_eval.sh $HOME/private/corpora/pubmed 100


