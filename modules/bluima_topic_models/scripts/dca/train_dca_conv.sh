#!/bin/bash

step=10
estimation_iterations=20

cd "/home/mz/Documents/epfl/projet/corpora/twenty_newsgroups/dca"

rm dca.err
rm dca.alpha
rm dca.gamma
rm dca.struct
rm -r dca.comps

mphier -L$step -e dca
mphier -r -t 4 -X$estimation_iterations,LRS -e dca
for (( c=$step+$step; c<=400; c+=$step ))
do
   echo "At iteration $c"
   mphier -r -L$step -e dca
   mphier -r -t 4 -X$estimation_iterations,LRS -e dca
   dca2html -s 20 dca
   cp dca.html stats/dca$c.html 
done
