source("/home/mz/Documents/epfl/projet/scripts/common.R")

plot_scores <- function(filename, yrange) {
  scores <- read.csv(file=paste(logs, filename, sep=''), header=FALSE)
  
  pdf(paste(figs, filename, ".pdf", sep=''))
  par(cex=1.7)
  plot(scores$V1, type="l", col="red",
       xlab="Iterations",ylab="DCA Score", ylim=yrange)
  
  dev.off()
  
}


plot_scores("dca-pubmed-ns-k300-v144000.dat", c(12, 22))
plot_scores("dca-pubmed-ns-k400-v265000.dat", c(12, 23))
plot_scores("dca-pubmed-ns-k50-v144000.dat", c(12, 20))