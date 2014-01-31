#!/bin/bash

function printUsage {
    echo "Runs PLDA"
    echo "Usage: runPlda.sh [--train |--eval] PLDA_ROOT_DIRECTORY"
}


PLDA_BIN="/home/mz/Documents/epfl/projet/plda"
LDA="$PLDA_BIN/lda"
#LDA="mpiexec -n 1 $PLDA_BIN/lda"

INFER="$PLDA_BIN/infer"

if [ "$#" -eq 2 ] 
then
    dataRoot=$2

    if [ "$1" = "--train" ]
    then
	trainingFile="$dataRoot/plda_train0"

	# order of arguments matters!
	
	$LDA --num_topics 30 --alpha 1.6666 --beta 0.01 \
	     --training_data_file $trainingFile \
	     --model_file $dataRoot/lda_model.txt --burn_in_iterations 80 \
	     --total_iterations 100
    elif [ "$1" = "--eval" ]
    then
	testFile="$dataRoot/plda_test"

	$INFER --num_topics 20 --alpha 0.1 --beta 0.01 \
	       --inference_data_file $testFile \
	       --inference_result_file $dataRoot/inference.txt \ 
	       --model_file $dataRoot/lda_model.txt \
	       --total_iterations 15 \
	       --burn_in_iterations 10
    else
	printUsage
    fi
else
    printUsage
fi
