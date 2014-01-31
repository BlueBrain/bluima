#!/bin/bash

function printUsage {
    echo "Runs Vowpal Wabbit"
    echo "Usage: runVw.sh [--train |--eval] VW_ROOT_DIRECTORY"
}


VW="$HOME/private/vowpal/vowpalwabbit/vw"

if [ "$#" -eq 2 ] 
then
    dataRoot=$2

    if [ "$1" = "--train" ]
    then
	trainingFile="$dataRoot/vw_train"

	$VW --lda 20 \
	--lda_alpha 0.1 --lda_rho 0.1 --lda_D 19900 \
	--minibatch 100 --power_t 0.5 --initial_t 1 \
	-b 16 \
	--cache_file /tmp/vw.cache --passes 700 \
	-p "$dataRoot/predictions.dat" \
	--readable_model "$dataRoot/topics.dat" \
	-d $trainingFile \
	-f "$dataRoot/model.dat"
    elif [ "$1" = "--eval" ]
    then
	testFile="$dataRoot/plda_test"

	echo "TODO"
    else
	printUsage
    fi
else
	printUsage
fi
