a <- commandArgs(trailingOnly=TRUE)
file <- a[1]
scores <- read.csv(file=file, header=FALSE, sep=' ')
x <-  c(5,10,15,18,20,22,25,30,40,50,75,100,150,200)

length(scores$V1)
length(x)
if (!interactive()) pdf("dca_topic_scores.pdf")
plot(x, scores$V1, type="b", col="red",
     xlab="Nr of Topics",ylab="Score",)
if (!interactive()) dev.off()
