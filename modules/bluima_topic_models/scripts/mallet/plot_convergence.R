a <- commandArgs(trailingOnly=TRUE)

d <- read.csv(file=a[1])

pdf(paste(c(a[1], ".pdf"), ''))
x <- 10*(1:length(d[[1]]))
plot(x, -d[[1]], type='l', xlab="Iterations", ylab="Score")

dev.off()
