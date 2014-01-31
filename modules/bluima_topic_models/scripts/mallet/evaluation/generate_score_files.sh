#!/bin/bash

if [ "$#" -eq 1 ] 
then
file=$1

# a line containing estimated scores looks like this: <100> LL/token: -8.86943
sed -n 's/^<[0-9]*> LL\/token: \(-[0-9.]*\).*$/\1/p' $file > $file.dat

grep "Test corpus likelihood" $file |  awk '{print $4}' > $file.test.dat

else
    echo "Filters out estimated and calculated scores from log files"
    echo "Usage: generate_score_files.sh path_to_some_log_file"

fi
