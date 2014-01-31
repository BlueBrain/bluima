#!/bin/bash

corpus_root=$1
MALLET=$HOME/private/software/mallet/bin/mallet
estimator_path="$HOME/private/topic_models"


logfile="$HOME/private/logs/mallet_eval_$(basename $corpus_root)_noopt2.log"

topics=$2
# Griffiths and Steyvers proposal
#alpha=$(echo "scale=4;50/$topics" | bc)
alpha_sum=50
alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01

iterations=2500
opt_interval=10 # optimize hyperparameter so and so cycles
est_cycles=25

working_dir="$corpus_root/mallet"
folds=10 # number of folds

cd $working_dir

folds_done=`cat foldscnt`

if [ $folds_done = ""]
then
        rm $logfile
        folds_done="-1"
fi

for((i=$folds_done + 1; i<folds; i+=1))
do
    echo "DOING FOLD $i" >> $logfile
    date >> $logfile
    pwd

    # prepare data
    $MALLET import-file --preserve-case --keep-sequence --name 0 --label 0 --data 1 --line-regex '^(.*)$' --token-regex '\S*' \
	--input "$corpus_root/training$i.txt" --output train.mallet

     \time -o mallet.usage -a -f "%e %D %K %M" $MALLET train-topics --input train.mallet \
	--output-state final_state --inferencer-filename model\
	--num-topics $topics --num-iterations $iterations --alpha $alpha_sum --beta $beta --num-threads 1  >> $logfile 2>&1

#--optimize-interval $opt_interval
#
    # estimate model
    cd $estimator_path
    pwd
    #TODO this class has a typo
    sbt "run-main ch.epfl.bbp.uima.topicmodels.estimator.MalletModelEstimation -c $est_cycles $working_dir/final_state $corpus_root/test$i.txt $corpus_root/vocabulary.txt" >> $logfile 
    
    cd "$working_dir"

    echo "$i" > foldscnt
done

cp --preserve=timestamps /tmp/eval* $working_dir
