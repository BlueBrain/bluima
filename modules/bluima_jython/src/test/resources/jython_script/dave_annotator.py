# Python imports. for now, only Python std library is possible
import re

# importing Java packages 
# see http://wiki.python.org/jython/UserGuide#accessing-java-from-jython
from org.apache.uima.jcas.tcas import Annotation

# jcas object is made available to this script. use it like any python object.
text = jcas.getDocumentText();

regex = re.compile("Dave");
match = regex.search(text);

if (match):
  # Create annotation and add it to jcas. This is then accessible from UIMA
  annotation = Annotation(jcas, match.start(), match.end());
  annotation.addToIndexes();