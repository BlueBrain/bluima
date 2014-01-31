#!/bin/bash

estimator_path="$HOME/private/topic_models"
LDA="$HOME/private/plda/lda"

corpus_root=$1
topics=$2
logfile="$HOME/private/logs/plda_eval_$(basename $corpus_root)_noopt.log"

# Griffiths and Steyvers proposal
alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01

iterations=350
est_cycles=25

avg_after=$(echo "$iterations-20" | bc)

working_dir="$corpus_root/plda"
folds=10 # number of folds

cd $working_dir

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
    pwd

    \time -o plda.usage -a -f "%e %D %K %M" $LDA --num_topics $topics --alpha $alpha --beta $beta \
	     --training_data_file plda_train$i \
	     --model_file lda_model.txt --burn_in_iterations $avg_after \
	     --total_iterations $iterations >> $logfile

    corpus_size=$(wc -w "$corpus_root/training$i.txt" | awk '{print $1}')
   
    # estimate model
    cd $estimator_path
    pwd
    sbt "run-main ch.epfl.bbp.uima.topicmodels.estimator.PLDAModelEstimation -a $alpha -b $beta -t $corpus_size -c $est_cycles $working_dir/lda_model.txt $corpus_root/test$i.txt $corpus_root/vocabulary.txt" >> $logfile 
    
    cd $working_dir

    echo "$i" > foldscnt

done

