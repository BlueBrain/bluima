#! /usr/bin/R --vanilla -q -f

source("/home/mz/Documents/epfl/projet/scripts/common.R")

scales <- paste(logs, "scale/", sep='')

plot_graph <- function(d, filename, title) {
  pdf(paste(figs, filename, sep=''))
  par(cex=2)
  plot(d$V1, d$V2/max(d$V2), xlab="Threads", ylab="Relative Runningtime", type="b", main=title)
  #TODO deriv plot(diff(d$V2), xlab="Threads/Processors", ylab="Relative Runtime", type="b", main=title)
  #text(3, 500, paste("Speedup from 1 to 4 processors", d$V2[0]/d$V2[3], sep=" "))
  dev.off()
}

d <-read.table(paste(scales, "dca_pubmed_scale.dat", sep=''), header = FALSE)
plot_graph(d, "dca_scale.pdf", "DCA")

d <-read.table(paste(scales, "mallet_pubmed_scale.dat", sep=''), header = FALSE)
plot_graph(d, "mallet_scale.pdf", "Mallet")

d <-read.table(paste(scales, "plda_pubmed_scale.dat", sep=''), header = FALSE)
#d$V2 <- d$V2*d$V3/30
plot_graph(d, "plda_scale.pdf", "PLDA")