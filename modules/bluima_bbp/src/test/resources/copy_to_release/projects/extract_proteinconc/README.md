
## 20131116_slurm
aborted during processing, due to some bug

## 20131120_slurm
large scale pre-processing
lots of failures due to null text in jCas -> restarted, see 20131209_slurm
took ~1week, approx 600k gz's files
preprocessing output in /nfs4/bbp.epfl.ch/simulation/nlp/data/20131120_preprocessed
co-occurences output mostly unusable since no keepOnlyNearestNeighbors yet (combinatorics explosion)

## 20131204_slurm
re-run on pre-processed from above, with keepOnlyNearestNeighbors
took 1-2 hours
subcell & cell did not work!

## 20131205_slurm
just for ABA brain regions
but using JSRE filter model
yielded around 75k co-occurrences

## 20131209_slurm
* no co-occurences extraction, just since had to redo some preprocessing
* based on 20131120_extract_proteinconc
took ~5d
/nfs4/bbp.epfl.ch/simulation/nlp/data/20131120_preprocessed$ find . -name '*.gz'  | wc -l
1197278

## 20131215_slurm
re-run on all pre-processed from above
entities:
* br from ABA-syn
* br from BRAINER
* proteins from BANNERM
* measures
* cell, subcell
co-occurrences:
* br <-> br, using JSRE
* prot-conc <-> br
* prot-conc <-> cell
* prot-conc <-> subcell
indexing with ElasticSearch

