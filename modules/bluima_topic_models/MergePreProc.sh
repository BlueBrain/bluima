

mvn -o exec:java \
-Dexec.mainClass="ch.epfl.bbp.uima.topicmodels.preprocessing.MergePreProc" \
-Dexec.classpathScope=runtime \
-Dblue_uima_home=../../. \
-Dexec.args="src/test/resources/MergePreProc"

