# R -f plot_timing.R --args ../../data/dca_timings.dat
a <- commandArgs(trailingOnly=TRUE)
file <- a[1]

iterations <- 50

d <- read.csv(file=file, header=FALSE)

nr_docs <- d[ seq(1,  length(d[,1]), 2),]
secs <- d[seq(2,  length(d[,1]), 2), ]/iterations

if (!interactive()) pdf("../../plots/dca_timings.pdf")

plot(nr_docs, secs, type="b", col="red",
     xlab="Number of abstracts",ylab="Seconds per Iteration")

if (!interactive()) dev.off()
