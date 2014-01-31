#!/bin/bash

# train LDA model

source incl.sh

cd $CORPUS_HOME

LOGFILE="training.log"

$MALLET train-topics --input "train.mallet" \
	--output-doc-topics "topic_dist_per_doc" --inferencer-filename "model" \
	--evaluator-filename "evaluator" --output-state "state" \
	--num-topics 50 --num-iterations 1000 --num-threads 2 > $LOGFILE 2>&1


# extract scores printed out during training --> make plot to assess convergence
sed -n 's/^<[0-9]*> LL\/token: \(-[0-9.]*\).*$/\1/p' $LOGFILE > scores.dat

# make plot of convergence
R --vanilla -f plot_convergence.R --args scores.dat
