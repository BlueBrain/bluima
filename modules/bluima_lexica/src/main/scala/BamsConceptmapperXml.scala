object BamsConceptmapperXml extends App {

  val src = scala.io.Source.fromFile("/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/swanson-98.parts_s.tsv")
  val iter = src.getLines().drop(1).map(_.split("\t"))

  println("""<?xml version="1.0" encoding="UTF-8" ?><synonym>""")
  iter.foreach { e =>
    println(<token canonical={ e(1) } ref_id={ e(0) }><variant base={ e(1) }/></token>)
  }
  println("""</synonym>""")
}