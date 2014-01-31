#!/bin/sh

mvn -o exec:java \
-Dexec.mainClass="ch.epfl.bbp.uima.RunPipeline" \
-Dexec.classpathScope=runtime \
-Dblue_uima_home=../../. \
-Dexec.args="$1 $2 $3 $4 $5 $6 $7 $8 $9 $10"
