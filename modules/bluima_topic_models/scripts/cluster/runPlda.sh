#!/bin/bash

function printUsage {
    echo "Runs PLDA"
    echo "Usage: runPlda.sh [--train |--eval] PLDA_ROOT_DIRECTORY"
}


PLDA_BIN="$HOME/private/plda"
#LDA="$PLDA_BIN/lda"
LDA="mpiexec -n 4 $PLDA_BIN/mpi_lda"

INFER="$PLDA_BIN/infer"

if [ "$#" -eq 2 ] 
then
    dataRoot=$2

    if [ "$1" = "--train" ]
    then
	trainingFile="$dataRoot/plda_train"

	# order of arguments matters!
	
	$LDA --num_topics 20 --alpha 0.1 --beta 0.01 \
	     --training_data_file $trainingFile \
	     --model_file $dataRoot/lda_model.txt --burn_in_iterations 20 \
	     --total_iterations 50
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
