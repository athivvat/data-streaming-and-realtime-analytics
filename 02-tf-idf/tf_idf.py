# TF-IDF Example Code
# https://github.com/apache/spark/examples/src/main/python/mllib/tf_idf_example.py

from pyspark import SparkContext
from pyspark.mllib.feature import HashingTF, IDF

if __name__ == "__main__":
    sc = SparkContext(appName="TFIDF")  # SparkContext

    # $example on$
    # Load documents (one per line).
    documents = sc.textFile("./README.md").map(lambda line: line.split(" "))

    hashingTF = HashingTF()
    tf = hashingTF.transform(documents)

    # While applying HashingTF only needs a single pass to the data, applying IDF needs two passes:
    # First to compute the IDF vector and second to scale the term frequencies by IDF.
    tf.cache()
    idf = IDF().fit(tf)
    tfidf = idf.transform(tf)

    # spark.mllib's IDF implementation provides an option for ignoring terms
    # which occur in less than a minimum number of documents.
    # In such cases, the IDF for these terms is set to 0.
    # This feature can be used by passing the minDocFreq value to the IDF constructor.
    idfIgnore = IDF(minDocFreq=2).fit(tf)
    tfidfIgnore = idfIgnore.transform(tf)
    # $example off$

    print("tfidf:")
    for each in tfidf.collect():
        print(each)

    print("tfidfIgnore:")
    for each in tfidfIgnore.collect():
        print(each)

    sc.stop()