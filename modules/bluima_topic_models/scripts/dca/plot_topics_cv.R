#R -f plot_topics_cv.R --args ../../data/dca_20n_topics_cv_cluster.log.test.dat
a <- commandArgs(trailingOnly=TRUE)
file <- a[1]

x = c(5,10,15,20,25,30,50,75,100,150,200)
nr_topics <- length(x)

folds <- 10
ci_up <- 9
ci_low <- 2


d <- read.csv(file=file, header=FALSE, sep=' ')
scores <- sapply(0:folds, function(x) d[x*nr_topics+(1:nr_topics),])

median <-  apply(scores, 1, function(x) median(x, na.rm=TRUE))
upper <- apply(scores, 1, function(x) sort(x)[ci_up])
lower <- apply(scores, 1, function(x) sort(x)[ci_low])



if (!interactive()) pdf("../../plots/dca_topics_cv.pdf")
plot(x,median, col="green", type="b",
     ylab="Neg. Log Likelihood per Token",xlab="Number of Topics",
     ylim=range(c(10.5, 12)))
legend(110, y=12, legend=c("Median Likelihood", "95% Confidence Interval"),
       col=c("green", "red"), lty=1)

lines(x, upper, col = "red", lty = 2)
lines(x, lower, col = "red", lty = 2)

abline(v=20, col = "black", lty=3)
text(35,y=12, "20 Topics")

if (!interactive()) dev.off()
