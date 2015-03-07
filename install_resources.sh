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

pushd "tests/bluima_dummymodelproxy" > /dev/null

mvn --quiet test

popd > /dev/null

