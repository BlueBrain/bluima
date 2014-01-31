#!/bin/bash

if [ "$#" -eq 1 ] 
then
file=$1

#Loglikelihood: -1.70682e+07
sed -n 's/^Loglikelihood: \(-[0-9.e+]*\)\s*$/\1/p' $file > $file.dat

grep "Test corpus likelihood" $file |  awk '{print $4}' > $file.test.dat

else
    echo "Filters out estimated and calculated scores from log files"
    echo "Usage: generate_score_files.sh path_to_some_log_file"

fi
