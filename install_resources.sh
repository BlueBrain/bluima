#!/bin/sh

echoerr() { echo "$@" 1>&2; }

for res in resources/*
do
    if [ -d "$res" ]
    then
        pushd "$res" > /dev/null
        mvn --quiet install
        status=$?
        popd > /dev/null
        if [ $status -ne 0 ]
        then
            echoerr "FAILED TO INSTALL $res"
            exit -1
        else
            echo "SUCCESSFULLY INSTALLED $res"
        fi
    fi
done

# Additionally, test the installed models

# TODO update script to automatically create tests for
# any class that implement ModelResource interface.
#  * grep "implement ModelResouce"
#  * get file name (and assume it's also the class name)
#  * find the pom.xml corresponding to that resource
#    (simply go up in file hierarchy) and extract info
#  * add dependency in bluima_dummymodelproxy
#  * add test class based on stubs in
#    tests/bluima_dummymodelproxy/src/test/java/ch/epfl/bbp/nlp

pushd "tests/bluima_dummymodelproxy" > /dev/null

mvn --quiet test

popd > /dev/null

