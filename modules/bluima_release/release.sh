#!/bin/bash

RELEASE=release_`date +"%Y%m%d"`
echo "Creating release in $RELEASE"
if [ -e $RELEASE ]; then 
	echo "Release '$RELEASE' already exists, exiting."; exit; 
fi
mkdir $RELEASE
mkdir $RELEASE/resources

# install all modules
cd ../.. # project root
mvn clean install -P appassembler_release -Dmaven.test.skip=true -Dappassembler.resources=../bluima/$RELEASE/resources
rc=$?
if [[ $rc != 0 ]] ; then
  echo 'could not build modules'; cd -; exit $rc
fi
cd - 

# package bluima module with appassembler
mvn clean package appassembler:assemble -Dmaven.test.skip=true
rc=$?
if [[ $rc != 0 ]] ; then
  echo "could not package release"; exit $rc
fi
mv target/appassembler/* $RELEASE/.

# create documentation
mvn -o exec:java \
-Dexec.mainClass="ch.epfl.bbp.uima.Documentation" \
-Dexec.classpathScope=runtime \
-Dblue_uima_home=../../.
rc=$?
if [[ $rc != 0 ]] ; then
  echo "could create documentation"; exit $rc
fi
mv target/DOCUMENTATION.html $RELEASE/.

# copy sample scripts and README
mkdir $RELEASE/pipelines
cp -R src/test/resources/copy_to_release/* $RELEASE/pipelines/.
cp README_release $RELEASE/README.txt
chmod 744 $RELEASE/bin/run_pipeline
# remove svn folders
find $RELEASE/pipelines -name ".svn" -type d -delete

echo '\n\n'
cd $RELEASE/pipelines/examples/
sh run_all_examples.sh

echo "\n\n************************************************\nDone creating release in '$RELEASE'\ndocumentation is in README.txt\n************************************************\n\n"