#!/bin/bash

MALLET=$HOME/private/software/mallet/bin/mallet

working_dir=$1

cd $working_dir

# import data
$MALLET import-file --preserve-case --keep-sequence --name 0 --label 0 --data 1 --line-regex '^(.*)$' --token-regex '\S*' \
        --input "../$2" --use-pipe-from train.mallet --output test.mallet

$MALLET infer-topics --inferencer model --input test.mallet \
    --output-doc-topics inferred_topics.txt --num-iterations 100
