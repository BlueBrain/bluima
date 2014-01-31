#!/bin/sh

# set ClassPath from BlueUima PEARS

## --------------------------------------------------------
## includes standard ClassPath

. "$UIMA_HOME"/bin/setUimaClassPath.sh

## --------------------------------------------------------
## checks for UIMA_PEARS

if [ "$UIMA_PEARS" = "" ]
then
  echo "UIMA_PEARS environment variable is not set" 1>&2 # send to std_err
  exit 1
fi

## --------------------------------------------------------
## function to add to UIMA_CLASSPATH only if value not
## already in there.

appendsToUIMA_CLASSPATH ()
{
    if [ "$UIMA_CLASSPATH" != '' ]; then
        IFS=:
        for i in $UIMA_CLASSPATH; do
            if [ "$1" = "$i" ]; then
                return 0
            fi
        done
        UIMA_CLASSPATH="$UIMA_CLASSPATH":"$1"
    else
        UIMA_CLASSPATH="$1"
    fi
}

## --------------------------------------------------------
for pear in "$UIMA_PEARS"/*
do
	if [ -d "$pear" ]
	then
		for sub in lib resources
		do
			if [ -d "$pear"/"$sub" ]
			then
				appendsToUIMA_CLASSPATH "$pear"/"$sub"
			fi
		done
	fi
done

export UIMA_CLASSPATH
