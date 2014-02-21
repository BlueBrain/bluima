#!/bin/bash
#!/bin/sh

MAVEN="mvn -Dmaven.test.skip=true"

RELEASE=bluima_`date +"%Y%m%d"`
echo "Creating release in $RELEASE"
if [ -e "$RELEASE" ]; then
	echo "Release '$RELEASE' already exists, exiting."; exit;
fi
mkdir "$RELEASE"
mkdir "$RELEASE"/resources

# install all bluima modules
cd ../.. # project root
$MAVEN clean install -P appassembler_release -Dmaven.test.skip=true -Dappassembler.resources=../bluima_bbp/"$RELEASE"/resources
rc=$?
if [[ $rc != 0 ]] ; then
  echo 'could not build modules'; cd -; exit $rc
fi
cd -

# package bluima_bbp module with appassembler
$MAVEN package appassembler:assemble -Dmaven.test.skip=true
rc=$?
if [[ $rc != 0 ]] ; then
  echo "could not package release"; exit $rc
fi
mv target/appassembler/* "$RELEASE"/.

# copy sample scripts and README
mkdir "$RELEASE"/pipelines
cp -R src/test/resources/copy_to_release/* "$RELEASE"/pipelines/.
cp README_release "$RELEASE"/README.txt
chmod 744 "$RELEASE"/bin/run_pipeline
# remove svn folders
find "$RELEASE"/ -name ".svn" -exec rm -rf {} \; > /dev/null

# create documentation
$MAVEN exec:java -Dexec.mainClass="ch.epfl.bbp.uima.Documentation" \
-Dexec.classpathScope=runtime -Dblue_uima_home=../../.
rc=$?
if [[ $rc != 0 ]] ; then
  echo "could not create documentation"; exit $rc
fi
mv target/DOCUMENTATION.html $RELEASE/.


# run tests
echo '\n\n'
cd "$RELEASE"/pipelines/examples/
sh run_all_examples.sh

echo "\n\n************************************************\nDone creating release in '$RELEASE'\ndocumentation is in README.txt\n************************************************\n\n"
