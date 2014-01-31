
# Script fragments to guide through entire training cycle
# using the PubMed Neurosience corpus
# The different parts (preprocessing and training) should probably be split into several files.
# IMPORTANT: not executable!
# 

# To build and install DCA see
# https://bbpteam.epfl.ch/project/spaces/display/NLP/DCA+instalation+and+model+training

################################################################################
# CONFIGURATION                                                                #
################################################################################

# general parameters to configure
WDIR="~/dca_working_dir" # DCA working directory
BLUE_UIMA_HOME="$HOME/blue_uima_trunk"
SBT_JAR="$HOME/private/software/sbt/sbt-launch-0.12.0.jar"

# DCA parameters
STEM="pubmed_ns"
TOPICS=100

ITERATIONS=200 # iterations of Gibbs sampling
THREADS=10
BLOCKS=3 # number of blocks used for data-structures
MEM_DOCS=2000 # Memory [MB] available in memory to store documents

# derived parameters
TOPIC_MODEL_HOME="$BLUE_UIMA_HOME/projects/blue_uima_topic_models"

################################################################################
# PREPROCESS FILES                                                             #
################################################################################

cd "$TOPIC_MODEL_HOME"

# Generate statistics over token frequencies
# The parameters in the code PubmedFtTokenFrequencyCounter have to
# be adapted ! Or even better, the code should be refactorized...
# SBT is launched this way to configure the amount of memory
# available, as much is needed.
# Will take some time (~7h)...
java -Xmx9000M  -Dblue_uima_home="$BLUE_UIMA_HOME" \
  -jar $SBT_JAR "run-main ch.epfl.bbp.uima.topicmodels.preprocessing.PubmedFtTokenFrequencyCounter"


# Preprocess the PubMed NeuroScience corpus
# * The parameters in the code PubmedPreprocessor have to be adapted!
# Will take some time (~7h)...
java -Xmx5000M  -Dblue_uima_home="$BLUE_UIMA_HOME" \
    -jar $SBT_JAR "run-main ch.epfl.bbp.uima.topicmodels.preprocessing.PubmedPreprocessor"

# Generate files for DCA

cd "$WDIR"

# In this directory, we should have something like pubmed_ns.tokens
# and pubmed_ns.txtbag

# generate .cnf file
echo "input=\"$STEM.txtbag\"" > "$STEM.cnf"
echo "baggedinput" >> "$STEM.cnf"

# Generate binary data for DCA
mpdata "$STEM"

# add model parameters to DCA .par file
echo "components=$TOPICS" >> "$STEM.par"
echo "alpha_update" >> "$STEM.par"

# We should be ready for training now


################################################################################
# TRAINING                                                                     #
################################################################################

# Do multi-threaded training. Print scores after every iteration. Store
# model every 10 iterations. Write log to $STEM.err
mphier -e -L "$ITERATIONS" -t "$THREADS" -b "$BLOCKS" -d "$MEM_DOCS" \
    -R 1 -C 20 "$STEM"

# Once training is finished, generate some additional files.

dca2html "$STEM" # generates HTML report

mpupd -T "$STEM" # generates .theta file (word-by-topics matrix) ->
		 # used for inference and estimation

# Retrieves topic distributions over entire corpus -> used for
# inference and likelihood estimation
mpupd -T -t 1,1 "$STEM" | cut -d"(" -f2 | cut -d")" -f1 > "$STEM".topics

# Retrieves topic distributions for all documents in training set
# -> used for MeSH correlation
# WARNING: large file (several gigabytes. For PubMed abstracts with
# K=200 it was 17GB)

#mpupd -m 0 "$STEM" > "topic_assignments"

