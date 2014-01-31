# likely command R -f plot_convergence_cv.R --args ../../data/dca_20n_convergence_cv_cluster.log.dat ../../data/dca_20n_convergence_cv_cluster.log.test.dat
a <- commandArgs(trailingOnly=TRUE)
file <- a[1]
file_test <- a[2]

iterations <- 700
step <- 20
folds <- 10
output_freq <- 5 # DCA outputs every 5 iterations

ci_up <- 9
ci_low <- 2

pts_per_fold_tr<- iterations/output_freq- 1 # no output at very first iteration

d <- read.csv(file=file, header=FALSE, sep=' ')
train <- sapply(0:folds, function(x) d[x*pts_per_fold_tr+(1:pts_per_fold_tr),])

median_tr <-  apply(train, 1, function(x) median(x, na.rm=TRUE))
upper_tr <- apply(train, 1, function(x) sort(x)[ci_up])
lower_tr <- apply(train, 1, function(x) sort(x)[ci_low])

pts_per_fold <- iterations/step
d <- read.csv(file=file_test, header=FALSE, sep=' ')
test <- sapply(0:folds, function(x) d[x*pts_per_fold+(1:pts_per_fold),])

median_ts <-  apply(test, 1, function(x) median(x, na.rm=TRUE))
upper_ts <- apply(test, 1, function(x) sort(x)[ci_up])
lower_ts <- apply(test, 1, function(x) sort(x)[ci_low])

length(median_tr)
length(1:(iterations/output_freq))

if (!interactive()) pdf("../../plots/dca_convergence_cv_train.pdf")
#par(mar = c(5, 4, 4, 4) + 0.3)

ylim1 = c(12, 13)
par(cex=1.5)
plot(output_freq*(1:pts_per_fold_tr), median_tr, type="l", col="red",
     ylim=range(ylim1), main='Score on Training Sets', xlab='Iterations', ylab='', yaxt='n')

lines(output_freq*(1:pts_per_fold_tr), upper_tr, col = "red", lty = 2)
lines(output_freq*(1:pts_per_fold_tr), lower_tr, col = "red", lty = 2)

axis(2, pretty(c(10, 16)), col='red')
title(ylab = 'Score')

if (!interactive()) dev.off()

# Plotting tests
if (!interactive()) pdf("../../plots/dca_convergence_cv_test.pdf")
par(cex=1.5)
ylim2 = c(10.5,12)
plot(step*(1:pts_per_fold), median_ts, col = "green", type="l", ylim=range(ylim2), main='Likelihood on Validation Sets', xlab='Iterations', ylab='Neg. log likelihood per token')
lines(step*(1:pts_per_fold), upper_ts, col = "green", lty = 2)
lines(step*(1:pts_per_fold), lower_ts, col = "green", lty = 2)

if (!interactive()) dev.off()


