#R -f plot_doc_topic_distribution.R --args ~/Desktop/pubmed_pre/out 75

a <- commandArgs(trailingOnly=TRUE)
file <- a[1]

# mpupd -m 0 dca | grep "^19952[.]*" | sed 's/:/ /g' > out
# assume topics on one line as output by mpupd -m (":" replaced by "
# ")
# e.g. "19952   1 0.156627 8 0.373494 9 0.289157 11 0.180723"

topics <- as.numeric(a[2])

d <- data.matrix(read.csv(file=file, header=FALSE, sep=" "))

d
topics
dist <- as.vector(d[1, 3:(topics+2)])
 sum(dist)
dist
#indices <- as.vector(d[1, seq(4,  length(d[1,]), 2)] + 1)
#values <- as.vector(d[1, seq(5,  length(d[1,]), 2)])

#dist <- array(0, dim=c(topics))


#dist[ indices ]

#dist[ indices ] <- values # R arrays are 1 based

pdf("distribution of topics.pdf")
plot(dist, xlab="Topic Number",ylab="Probability", type='h', lwd=3)
dev.off()

