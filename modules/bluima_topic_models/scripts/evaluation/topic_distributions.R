#! /usr/bin/R --vanilla -q -f

source("/home/mz/Documents/epfl/projet/scripts/common.R")

plot_topics <- function(dist) {
 plot(dist/sum(dist),xlab="Topic Number",ylab="Probability", type='h', lwd=3) 
}

dca <- read.table(paste(logs, "topic_distributions/dca_pubmed.dat", sep=''), header = FALSE)
dca <- dca[,-1]

pdf(paste(figs, "dca_topics.pdf", sep=''))
par(mfrow=c(3,2))

apply(dca[1:6,], 1, plot_topics)
dev.off()

mallet <- data.matrix(read.table(paste(logs, "topic_distributions/mallet_pubmed.dat", sep=''), header = FALSE))
mallet <- mallet[, c(-1,-2)]

pdf(paste(figs, "mallet_topics.pdf", sep=''))
par(mfrow=c(3,2))
apply(mallet[1:6,], 1, function(d) {
  ind <- d[seq(1,  length(d), 2)]
  vals <- d[seq(2,  length(d), 2)]
  plot_topics(vals[order(ind)])
})
dev.off()

plda <- data.matrix(read.table(paste(logs, "topic_distributions/plda_pubmed.dat", sep=''), header = FALSE))
#pld <- mallet[, c(-1,-2)]

pdf(paste(figs, "plda_pubmed_topics.pdf", sep=''))
par(mfrow=c(3,2))
apply(mallet[1:6,], 1, function(d) {
  ind <- d[seq(1,  length(d), 2)]
  vals <- d[seq(2,  length(d), 2)]
  plot_topics(vals[order(ind)])
})
dev.off()