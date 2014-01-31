#!/bin/bash

estimator_path="$HOME/private/topic_models"
VW="$HOME/private/vowpal/vowpalwabbit/vw"

corpus_root=$1
topics=$2
logfile="$HOME/private/logs/vw_eval_$(basename $corpus_root)_noopt_check.log"

# Griffiths and Steyvers proposal
alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01

iterations=5000
est_cycles=25

batch_size=2048
words=`wc -l $corpus_root/vocabulary.txt`
log_words=18
#log_words=$(echo "l($words)/l(2)" | bc -l)

working_dir="$corpus_root/vw"
folds=1 # number of folds

cd $working_dir

rm foldscnt
folds_done=`cat foldscnt`
echo "fold already done: $folds_done"
if [ $folds_done = ""]
then
	rm $logfile
	folds_done="-1"
fi

for((i=$folds_done + 1; i<folds; i+=1))
do
    echo "DOING FOLD $i" >> $logfile
    pwd >> $logfile
    date >> $logfile
    documents=`wc -l vw_train$i | awk '{print $1}'`

    cache_file="/tmp/vw_$(basename $corpus_root).cache"
    rm $cache_file

    \time -o vw.usage -a -f "%e %D %K %M" $VW --lda $topics \
        --lda_alpha $alpha --lda_rho $beta --lda_D $documents \
        --minibatch $batch_size --power_t 0.5 --initial_t 1 --decay_learning_rate 0.5 -l 1 \
        -b $log_words \
        --cache_file $cache_file --passes $iterations \
        --readable_model "$working_dir/topics.dat" \
        -d "$working_dir/vw_train$i" \
        -f "$working_dir/model.dat" #>> $logfile 2>&1

    corpus_size=$(wc -w "$corpus_root/training$i.txt" | awk '{print $1}')
    # estimate model
    cd $estimator_path
    pwd

    sbt "run-main ch.epfl.bbp.uima.topicmodels.estimator.VWModelEstimation -a $alpha -b $beta -c $est_cycles -t $corpus_size $working_dir/topics.dat $corpus_root/test$i.txt $corpus_root/vocabulary.txt" >> $logfile 
    
    cd $working_dir

    echo "$i" > foldscnt
done

