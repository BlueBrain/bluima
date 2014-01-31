#!/bin/sh

mvn clean install exec:java \
-Dexec.mainClass="ch.epfl.bbp.uima.projects.misc.UpdateAbstractDb" \
-Dexec.classpathScope=runtime \
-Dblue_uima_home=../../.
