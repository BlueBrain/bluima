#!/bin/bash

LDA_INFER="$HOME/private/plda/infer"

working_dir=$1

topics=100
alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01


cd $working_dir
ls
$LDA_INFER --num_topics $topics --alpha $alpha --beta $beta \
           --inference_data_file plda_test9 \
           --inference_result_file inference.txt  --model_file lda_model.txt  --total_iterations 100 \
	   --burn_in_iterations 50
