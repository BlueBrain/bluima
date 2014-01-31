




## ABA/20131120_bluima_extractions$

    wc -l 20131127_br-aba-syn_cooc_br-only.tsv
    7'073'741


## 20131205_extract_br-br.pipeline

    # aggregate connectivity data
    base: /Volumes/simulation/nlp/data/20131205_br$
    mkdir aggregate
    cat *_br-aba-syn_cooc_jsre.txt >> aggregate/20131206_br-aba-syn_cooc_jsre.tsv
    cat *_br-aba-syn_cooc_nearestn.txt >> aggregate/20131206_br-aba-syn_cooc_nearestn.tsv
    wc -l 20131206_br-aba-syn_cooc_jsre.tsv
      155'540
    wc -l 20131206_br-aba-syn_cooc_nearestn.tsv
    1'281'258
    tar czf 20131206_br-aba-syn_cooc.tgz 20131206_br-aba-syn_cooc_jsre.tsv 20131206_br-aba-syn_cooc_nearestn.tsv

    # aggregate all ABA occurrences
    base /nfs4/bbp.epfl.ch/user/richarde/slurm_logs/20131205_prot/
    egrep -h $'^[0-9]+\taba:[0-9]+$' *-out.log > aggregate/aba_cnts.tsv
