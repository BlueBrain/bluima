from lxml import etree as ET
'''
strip unnecessary <sentenceanalyses>
'''

doc = ET.parse("WhiteTextNegFixFull.xml")

for bad in doc.xpath('//sentenceanalyses'):
    bad.getparent().remove(bad)

print(ET.tostring(doc))
