#!/bin/bash -l

#SBATCH --job-name="pubmed large"
#SBATCH --nodes=1
#SBATCH --ntasks=10
#SBATCH --ntasks-per-node=10
#SBATCH --mem=10000
#SBATCH --time=1-23:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-dca-pubmed-ft-ns-copy-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-dca-pubmed-ft-ns-copy-stderr.log
 
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
 
#srun -n 2 mpirun ./my-mpi.job
#3srun -n 2 mpirun ./my-othermpi.job

cd $HOME/private/corpora/pubmed_ns_copy/dca

#rm dca.err
mphier -e -t 10 -r -L1000 -d2000 -i 10000000 -C 20 dca


