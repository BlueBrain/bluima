#!/bin/bash

# estimate likelihood of unseen documents document
source incl.sh

$MALLET evaluate-topics --input "$CORPUS_HOME/test.mallet" --evaluator "$CORPUS_HOME/evaluator" --output-doc-probs "$CORPUS_HOME/document_likelihoods" \
		--use-resampling TRUE --num-iterations 25 > "$CORPUS_HOME/model_likelihood"


