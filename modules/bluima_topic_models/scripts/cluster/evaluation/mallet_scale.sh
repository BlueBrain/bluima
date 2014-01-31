#!/bin/bash -l
#SBATCH --job-name="Mallet Scale"
#SBATCH --nodes=1
#SBATCH --ntasks=12
#SBATCH --ntasks-per-node=12
#SBATCH --mem=4096
#SBATCH --time=23:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-mallet-scale-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-mallet-scale-stderr.log
 
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

MALLET=$HOME/private/software/mallet/bin/mallet

corpus_root="$HOME/private/corpora/pubmed"
topics=100
logfile="$HOME/private/logs/mallet_eval_$(basename $corpus_root)_opt_time.log"

# Griffiths and Steyvers proposal
alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01

iterations=1200
est_cycles=25
opt_interval=10
#avg_after=$(echo "$iterations-20" | bc)

working_dir="$corpus_root/mallet"
folds=10 # number of folds

cd $working_dir

rm $logfile

for threads in {12,10,8,6,4,2,1}
do
    i=0
    echo "Processing fold $i with $threads threads" >> $logfile
    date >> $logfile
    
    pwd

   # prepare data
    $MALLET import-file --preserve-case --keep-sequence --name 0 --label 0 --data 1 --line-regex '^(.*)$' --token-regex '\S*' \
	--input "$corpus_root/training$i.txt" --output train.mallet

    startt=$(date "+%s")
    
     \time -o mallet.usage -a -f "%e %D %K %M" $MALLET train-topics --input train.mallet \
        --output-state final_state --inferencer-filename model\
        --num-topics $topics --num-iterations $iterations --optimize-interval $opt_interval  --num-threads $threads  >> $logfile 2>&1

#--optimize-interval $opt_interval 
     endt=$(date "+%s")
     echo "$startt .... $endt"
    echo "$endt - $startt" | bc >> $logfile
done
