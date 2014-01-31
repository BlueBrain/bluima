#!/usr/bin/Rscript

# Estimated score of 100 documents of some model using different number of cycles (each configuration ran 10 times)
d <- read.csv(file="../data/estimation_cycles.dat", header=FALSE, sep=' ')

pdf("../plots/estimation_cycles.pdf")
par(cex=1.7)
plot(d$V1, -d$V2, xlab='Cycles', ylab='Likelihood', type="b")  # median
lines(d$V1, -d$V3, lty = 2) # upper confidence interval
lines(d$V1, -d$V4, lty = 2) # lower confidence interval
dev.off()

