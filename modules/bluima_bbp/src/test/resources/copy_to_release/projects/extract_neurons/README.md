## Howto (20150501)


* sync typessytem
        sh 20150501_sync_files.sh
* go into bluima_typesystem, 
    * jcasgen (or profile?)
    * delete base Ruta TS java classes (org.apache.uima.ruta.type)
* run test script (20141104_neuroNER_scale.pipeline) to check all is ok 




### ES

* **need to create index first!**
        vim plugins/neuroner/_site/mapping.sh 
        sh plugins/neuroner/_site/mapping.sh
*   started manually in `richarde@SV-38-059:~/dev/bluebrain/neuroNER/20141016_neuroNER_viewer$nohup ./bin/elasticsearch &`
* cluster name
        http://128.178.51.50:9200/_cluster/health?pretty=true%27
        {
          "cluster_name" : "elasticsearch_neuroner",
* pipeline config
        ae: ch.epfl.bbp.uima.elasticsearch.NeuronIndexer
         indexName: neuroner_20150501
         clusterName: elasticsearch_neuroner
         host: 128.178.51.50
