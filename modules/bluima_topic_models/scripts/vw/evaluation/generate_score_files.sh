#!/bin/bash

if [ "$#" -eq 1 ] 
then
file=$1

# format:
# SOME HEADER
#average    since         example     example  current  current  current
#loss       last          counter      weight    label  predict features
#12.260107  12.260107           3         3.0  unknown   0.0000       59
#  ....

sed -n 's/^\([0-9.]*\s*\)[0-9.]*\s*\([0-9]*\).*$/\2 \1/p' $file | grep -v "^\s*$" > $file.dat

grep "Test corpus likelihood" $file |  awk '{print $4}' > $file.test.dat

else
    echo "Filters out estimated and calculated scores from log files"
    echo "Usage: generate_score_files.sh path_to_some_log_file"

fi
