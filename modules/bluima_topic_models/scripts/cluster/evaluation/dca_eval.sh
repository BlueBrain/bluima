#!/bin/bash

# path to scala codebase (bluima_topic_models folder)

estimator_path="$HOME/private/topic_models"

# path to (e.g. token_frequency, statistics.txt, ...)
corpus_root=$1
topics=$2
logfile="$HOME/private/logs/dca_eval_$(basename $corpus_root)_noopt_parallel.log"

# Griffiths and Steyvers proposal
#alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01

iterations=1200
threads=11
est_cycles=25

avg_after=$(echo "$iterations-20" | bc)

# contains dca model files (e.g. dca.par)
working_dir="$corpus_root/dca"
folds=10 # number of folds

cd $working_dir
cp dca.tokens dca_cv.tokens # hack: stem is dca_cv for cross valid

#folds_done=`cat foldscnt` # optimization, in case job is killed...
#if [ $folds_done = ""]
#then
#	rm dca_cv.err
#	rm $logfile
#	folds_done="-1"
#fi

for((i=0; i<folds; i+=1))
do
    echo "DOING FOLD $i" >> $logfile
    pwd
    # prepare data
    cp dca.txtbag$i dca_cv.txtbag
    mpdata dca_cv
    #testsize=`wc -l ../plda/plda_test$i | awk '{print $1}'` # hack
    #echo "testdocs=$testsize" >> dca_cv.par
    echo "alpha.alpha=$alpha+" >> dca_cv.par 
    echo "alpha_update" >> dca_cv.par # hyperparam opt. (only alpha, but good enough)
    echo "thetaprior=$beta" >> dca_cv.par
    echo "components=$topics" >> dca_cv.par
	
    \time -o dca.usage -a -f "%e %D %K %M" mphier -L$iterations -A$avg_after -t $threads dca_cv >> $logfile 2>&1 # train model

    # gen matrices, used in DCAModelEstimation
    mpupd -T -t 1,1 dca_cv | sed 's/Component [0-9]* (\([0-9.]*\)):/\1/g' > dca_cv.topics # TODO maybe use cut instead

    corpus_size=$(wc -w "$corpus_root/training$i.txt" | awk '{print $1}')
    echo "size of training corpus: $corpus_size" 
    # estimate model
    cd $estimator_path
    pwd

    sbt "run-main ch.epfl.bbp.uima.topicmodels.estimator.DCAModelEstimation -t $corpus_size -c $est_cycles $working_dir dca_cv $corpus_root/test$i.txt" >> $logfile 
    
    cd $working_dir

    echo "$i" > foldscnt
done

