#!/bin/bash

# infer p(z|d) from an unseen document

source incl.sh

$MALLET infer-topics --inferencer "$CORPUS_HOME/train.model" --input "$CORPUS_HOME/test.mallet" \
	--output-doc-topics "$CORPUS_HOME/test.txt" --num-iterations 100

