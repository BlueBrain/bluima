

#document_dist = '/Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_bbp/target/15970669.dist'
document_dist = '/Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_bbp/target/18982120.dist'

topics_dist = '/Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_topic_models/resources/models/20130309_preprocess_pubmed_ns.pipeline_min/dca/topics_dist.txt'
#topics_dist = '/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/1_dca/20130129_replicate_marcs/20130127_dca-pubmed_abstracts_trained_model/topics_dist.txt'

lines = [l.rstrip() for l in open(topics_dist).readlines() if len(l.rstrip()) > 0]
assert len(lines) == 200


topics = {}
for topic_id, line in enumerate(lines):
    topics[topic_id] = line.split(' ')[4:]


import pandas as pd

d = pd.read_table(document_dist)
d.columns = ['pz']
for z, p_z in d.query('pz > 0.03').sort(['pz'], ascending=False).itertuples():
    print z, p_z, [t[0:t.find('(')] for t in topics[z+1]]


