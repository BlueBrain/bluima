#!/bin/bash

VW="$HOME/private/vowpal/vowpalwabbit/vw"

cd $HOME/private/corpora/pubmed/vw/online

$VW -d "$HOME/private/corpora/pubmed/vw/online/vw_test9.dat" --lda 100 \
	--lda_alpha 0.5 --lda_rho 0.1 -i model.dat --testonly \
	-p predictions.dat \
	
 
