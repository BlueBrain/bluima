#!/bin/bash

# filter data:  grep -e "^[0-9].*"

# Calculates models for different corpus sizes 

iterations=50
estimation_iterations=10
threads=4
cd ../../corpora/pubmed_large/dca # change to where dca files are
				# stored


#for topics in 10

for docs in 1000 5000 10000 20000 50000 100000
do
	echo "At ITERATION $docs"
#	# adjust number of documents
	sed -e "s/^documents=\([0-9]*\)$/documents=$docs/g" dca.par > /tmp/dca
	mv /tmp/dca dca.par
	
	echo $docs >&2
	\time -f "%e" mphier -L$iterations -t $threads -e dca
done



