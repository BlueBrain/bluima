#!/bin/bash

source incl.sh

# one document per line with one label separated by a white space from the actual document
# assume tokens are separated by whitespaces
# numbers in --lable and --data denote regex group numbers from --line-regex
GENERIC_ARGS="import-file --preserve-case --keep-sequence --line-regex ^(\1) (.*)$ --name 0 --label 1 --data 2 --token-regex \S*"

TRAIN_OUT="$CORPUS_HOME/train.mallet" # outputfile
# generate training file
$MALLET $GENERIC_ARGS --input "$CORPUS_HOME/training.txt" --output $TRAIN_OUT

#test: important to reuse pipe!
#$MALLET $GENERIC_ARGS --use-pipe-from $TRAIN_OUT --input "$CORPUS_HOME/test.txt" --output "$CORPUS_HOME/test.mallet"



