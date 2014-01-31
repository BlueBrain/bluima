#!/bin/bash

function printUsage {
    echo "Runs Vowpal Wabbit"
    echo "Usage: runVw.sh [--train |--eval] VW_ROOT_DIRECTORY"
}


VW_BIN="vw"

if [ "$#" -eq 2 ] 
then
    dataRoot=$2

    if [ "$1" = "--train" ]
    then
	trainingFile="$dataRoot/vw_train0"

	vw --lda 30 \
	--lda_alpha 1.6666 --lda_rho 0.01 --lda_D 19900 \
	--minibatch 100 --power_t 0.5 --initial_t 1 \
	-b 16 \
	--cache_file /tmp/vw.cache --passes 100 \
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

#	-p "$dataRoot/predictions.dat" \
