#!/bin/bash

if [ "$#" -eq 1 ] 
then
file=$1

grep data.lp $file | sed 's/^.* lp=\([0-9.]*\).*$/\1/g' > $file.dat

grep Final $file | sed 's/^.*test.lp=\([0-9.nan-]*\).*$/\1/g' > $file.test.dat

#R --vanilla -f plot_convergence.R --args dca_convergence_lp dca_convergence_lp_test $step
else
    echo "Filters out estimated and calculated scores from dca.err log files"
    echo "Usage: dca_convergence.sh path_to_some_dca_err_file"

fi