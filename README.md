Bluima:: UIMA components at BlueBrain
=====================================

[![Build Status](https://travis-ci.org/BlueBrain/bluima.svg?branch=master)](https://travis-ci.org/BlueBrain/bluima)


### Papers

* Richardet, Renaud, Jean-Cédric Chappelier, and Martin Telefont.
  "Bluima: a UIMA-based NLP Toolkit for Neuroscience."
  Unstructured Information Management Architecture (UIMA) (2013): 34.
  ceur-ws.org/Vol-1038/paper_7.pdf


### Dependencies

* Java JDK 6 or higher (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Maven 3			   (http://maven.apache.org/download.html#Installation)


### Configure

Setup BLUIMA_HOME, e.g. (in your .bash_profile):

    export BLUIMA_HOME={absolute path to Bluima}


### Build

    > export MAVEN_OPTS="-Xmx4G -XX:MaxPermSize=256m"
    > mvn clean install -Dblue_uima_home={PATH_TO_HOME}

Get yourself a coffee. Rinse and repeat above command if error is "could not download artifacts"

If gpg signing is not setup and you simply want to skip it then you can add `-Dgpg.skip=true` to the maven command line options.


### Maven Tests

* The reference test environment is the command line.
* Run all tests
      > mvn clean test
* Skip tests while building
      > mvn install -Dmaven.test.skip=true


### Eclipse

* Tested with 4.2 and latest m2e plugin
* Make sure you have built the codebase with the above step (`mvn`)
* Import the module `bluima_bbp` with: File -> Import... -> Existing Maven Projects
* Set BLUIMA_HOME in Eclipse
 * Preferences: Java > Installed JRE > Edit
 * -Xms1G -Xmx4G -Dbluima_home={absolute path to Bluima}
* Test your installation (see "Getting Started" below)
* Import other modules you need with: File -> Import... -> Existing Maven Projects

### Release

    > cd modules/bluima
    > sh release.sh

The release can be deployed on any computer with a JRE (e.g. cluster).


### Conventions (over configuration)

* Maven projects are named bluima_{project-name}
* Class naming convetions
 * UIMA Analysis Engines are named   `Annotator`
 * UIMA Collection Readers are named `CollectionReader`
 * UIMA Cas Consumers are named      `Writer`
 * JUnit tests are named {base-class}Test

### Getting Started

* launch the class `ch.epfl.bbp.uima.RunPipeline` in project `bluima_bbp`
* select a pipeline to run, e.g. `examples/1_simple/simple.pipeline`


### Feedback

* is welcome: renaud.richardet@epfl.ch
