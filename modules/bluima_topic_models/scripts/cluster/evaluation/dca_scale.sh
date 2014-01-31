#!/bin/bash -l
#SBATCH --job-name="DCA Scale"
#SBATCH --nodes=1
#SBATCH --ntasks=12
#SBATCH --ntasks-per-node=12
#SBATCH --mem=4096
#SBATCH --time=23:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-dca-scale-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-dca-scale-stderr.log
 
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

corpus_root="$HOME/private/corpora/pubmed_scale"
topics=100
logfile="$HOME/private/logs/dca_eval_$(basename $corpus_root)_opt_time.log"

# Griffiths and Steyvers proposal
alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01

iterations=310
threads=1
est_cycles=25

#avg_after=$(echo "$iterations-20" | bc)

working_dir="$corpus_root/dca"
folds=10 # number of folds

cd $working_dir
cp dca.tokens dca_cv.tokens # hack

rm $logfile

for threads in {12,10,8,6,4,2}
do
    i=0
    echo "Processing fold $i with $threads threads" >> $logfile
    date >> $logfile
    startt=$(date "+%s")
    pwd

    # prepare data
    cp dca.txtbag$i dca_cv.txtbag
    mpdata dca_cv
    #testsize=`wc -l ../plda/plda_test$i | awk '{print $1}'` # hack
    #echo "testdocs=$testsize" >> dca_cv.par
    echo "alpha.alpha=$alpha+" >> dca_cv.par 
    echo "alpha_update" >> dca_cv.par
    echo "thetaprior=$beta" >> dca_cv.par
    echo "components=$topics" >> dca_cv.par
	
    \time -o dca.usage -a -f "%e %D %K %M" mphier -L$iterations -t $threads dca_cv >> $logfile 2>&1 # train model
    endt=$(date "+%s")
    echo "$endt - $startt" | bc >> $logfile
done
