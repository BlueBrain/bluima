#!/bin/bash -l
#SBATCH --job-name="PLDA Pubmed large"
#SBATCH --nodes=2
#SBATCH --ntasks=4
#SBATCH --ntasks-per-node=2
#SBATCH --mem=15000
#SBATCH --time=23:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-plda-pubmed-med-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-plda-pubmed-med-stderr.log

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

#module load mpich2-x86_64
#module load mvapich2-psm-x86_64

#LDA="mpiexec -n $2 $HOME/private/plda/mpi_lda"

LDA="$HOME/private/plda/lda"

dataRoot="$HOME/private/corpora/pubmed_complete/plda"
logfile="$HOME/private/logs/plda_pubmed_complete.log"
rm $logfile


topics=$1
alpha=0.1
beta=0.01

iterations=100
burn_in=90

trainingFile="$dataRoot/plda_train"
	
\time -o plda.usage -a -f "%e %D %K %M" $LDA --num_topics $topics --alpha $alpha --beta $beta --training_data_file $trainingFile --model_file $dataRoot/lda_model.txt --burn_in_iterations $burn_in --total_iterations $iterations #>> $logfile 2>&1
