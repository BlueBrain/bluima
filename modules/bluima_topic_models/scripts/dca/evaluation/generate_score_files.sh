#!/bin/bash

if [ "$#" -eq 1 ] 
then
file=$1

grep data.lp $file | sed 's/^.* lp=\([0-9.]*\).*$/\1/g' > $file.dat

grep "Test corpus likelihood" $file |  awk '{print $4}' > $file.test.dat

else
    echo "Filters out estimated and calculated scores from DCA log files"
    echo "Usage: generate_score_files.sh path_to_some_log_file"

fi
