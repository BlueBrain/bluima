Bluima:: UIMA components at BlueBrain
=====================================


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

    export BLUIMA_HOME=/Users/richarde/trunk


### Build

    > export MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=128m"
    > mvn clean install -Dblue_uima_home={PATH_TO_HOME}

Get yourself a coffee. Rinse and repeat above command if error is "could not download artifacts"


### Eclipse

* Tested with 4.2 and latest m2e plugin
* Make sure you have built the codebase with the above step
* Import the modules you need with: File -> Import... -> Existing Maven Projects
* Set BLUIMA_HOME in Eclipse
 * Preferences: Java > Installed JRE > Edit
 * -Xms1G -Xmx4G -Dbluima_home=/Users/richarde/trunk


### Maven Tests

* The reference test environment is the command line.
* Run all tests
    > mvn clean test
* Skip tests while building
    > mvn install -Dmaven.test.skip=true


### Release

    > cd modules/bluima
    > sh release.sh

The release can be deployed on any computer with a JRE.


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
