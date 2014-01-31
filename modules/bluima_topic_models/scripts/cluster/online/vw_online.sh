#!/bin/bash -l

#SBATCH --job-name="VW online"
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --ntasks-per-node=1
#SBATCH --mem=2000
#SBATCH --time=5:00:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-vw-online-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-vw-online-stderr.log
 
# In case there are per-group custom initialization files
#. /nfs4/bbp.epfl.ch/group/visualization/module/modules.bash
# Load your required module files here
#module load MODULE_TO_BE_LOADED
 
# To avoid Kerberos tickets becoming expired, run the following in
# the background to check every 30min and try to renew it
krenew -b -K 30


estimator_path="$HOME/private/topic_models"
VW="$HOME/private/vowpal/vowpalwabbit/vw"

corpus_root="$HOME/private/corpora/pubmed"
topics=100
logfile="$HOME/private/logs/vw_online_pubmed_noopt.log"

# Griffiths and Steyvers proposal
alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01

iterations=1
est_cycles=25

batch_size=2048
words=`wc -l $corpus_root/vocabulary.txt`
log_words=18
#log_words=$(echo "l($words)/l(2)" | bc -l)

working_dir="$corpus_root/vw/online"
parts=10 # number of files

cd $working_dir
documents=`wc -l vw_train9 | awk '{print $1}'`

rm $logfile
initial_t=1
learning_rate=1
power_t=0.5

for((i=0; i<parts; i+=1))
do
    echo "DOING PART $i" >> $logfile
    pwd >> $logfile
    date >> $logfile

    doc_in_part=`wc -l x$i | awk '{print $1}'`
    
    cache_file="/tmp/vw_cache"
    rm $cache_file




    common="$VW --lda $topics \
        --lda_alpha $alpha --lda_rho $beta --lda_D $documents \
        --minibatch $batch_size -l $learning_rate --power_t $power_t --initial_t $initial_t\
        -b $log_words \
         --passes $iterations \
	--cache_file $cache_file
        --readable_model topics.dat \
        -d x$i"

    if [ $i -eq 0 ] 
    then
	echo "first it"
	\time -o vw.usage -a -f '%e %D %K %M' $common -f "model.dat" >> $logfile 2>&1
    else
	echo "second"
	mv model.dat prev_model.dat
	\time -o vw.usage -a -f '%e %D %K %M' $common -i prev_model.dat -f model.dat >> $logfile 2>&1
    fi

    initial_t=$(echo "$initial_t + $iterations * $doc_in_part" | bc)
learning_rate=$(perl -le "print $initial_t**-$power_t")
    echo "Initial t is $initial_t" >> $logfile
    echo "Learning rate: $learning_rate" >> $logfile
done

    corpus_size=$(wc -w "$corpus_root/training9.txt" | awk '{print $1}')

    # estimate model
    cd $estimator_path
    pwd

    sbt "run-main ch.epfl.bbp.uima.topicmodels.estimator.VWModelEstimation -a $alpha -b $beta -c $est_cycles -t $corpus_size $working_dir/topics.dat $corpus_root/test9.txt $corpus_root/vocabulary.txt" >> $logfile 
   

