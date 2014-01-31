#R -f plot_doc_topic_distribution.R --args input output

a <- commandArgs(trailingOnly=TRUE)
file <- a[1]

d <- read.csv(file=file, header=FALSE)

pdf(paste(a[2], ".pdf", sep=''))
plot(d$V1/sum(d$V1), xlab="Topic Number",ylab="Probability", type='h', lwd=3)
dev.off()

