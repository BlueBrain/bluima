import json, urllib2

data = urllib2.urlopen('http://neuroelectro.org/api/1/a/?limit=500')

for article in json.load(data)['objects']:
    #print article
    with open('input/articles_neurelectro/{}.txt'.format(article['pmid']), 'w') as outf:
        print article['pmid']
        outf.write('{}\n{}\n'.format(\
            article['title'].encode("utf-8"), \
            article['abstract'].encode("utf-8")))
