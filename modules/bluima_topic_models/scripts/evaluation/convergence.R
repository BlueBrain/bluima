#! /usr/bin/R --vanilla -q -f
# likely command R -f plot_convergence_cv.R --args ../../data/dca_20n_convergence_cv_cluster.log.dat ../../data/dca_20n_convergence_cv_cluster.log.test.dat
#a <- commandArgs(trailingOnly=TRUE)

source("/home/mz/Documents/epfl/projet/scripts/common.R")


#file_test <- "/home/mz/Documents/epfl/projet/data/eval_logs/mallet_eval_twenty_newsgroups_noopt.log.test.dat" #a[2]
# data.test <- read.csv(file=file_test, header=FALSE, sep=' ')

plot_conv <- function(data, ylim, factor, x, title, yaxis_lab, xaxis_lab="Iterations", plot_median=TRUE, plot_es=FALSE) {
  print("Score goes up")
  conv <- which.min(apply(abs(data), 1, median))*x[1] # hack
  print(conv)
  par(cex=1.7)
  matplot(x, factor*data, ylim=ylim, xlab=xaxis_lab,ylab=yaxis_lab, main=title, pch='.', lwd=1)
  
  if(plot_median)
    lines(x, apply(abs(data), 1, median), lwd=3, col="red", type='l')

  if(plot_es) {
    abline(v=conv, col="red", lwd=2)
    text(1.3*conv, (mean(ylim) + ylim[2])/2, paste(conv, " Iterations"))
  }
}

mallet_plot <- function(data, file, ylim, title, plot_es=FALSE) {
  pdf(paste(figs, file, sep=""))
  plot_conv(data, ylim, -1, 10*c(1:length(data[,1])), title, "Mallet Score", plot_es=plot_es)
  dev.off()
}

dca_plot <- function(data, file, ylim, title, plot_es=FALSE) {
  pdf(paste(figs, file, sep=""))
  plot_conv(data, ylim, 1, 5*c(1:length(data[,1])), title, "DCA Score", plot_es=plot_es)
  dev.off()
}

plda_plot <- function(data, file, ylim, title, runtime) {
  pdf(paste(figs, file, sep=""))
  data <- t(t(data)/foldsizes[,1])
  plot_conv(data, ylim, -1, c(1:length(data[,1])), title, "PLDA Score/Token")
  dev.off()
}

vw_plot <- function(filename, file, ylim, title) {
  pdf(paste(figs, file, sep=""))
  file <- paste(elogs, filename, sep='')
  data.raw <- read.csv(file=file, header=FALSE, sep=' ')
  data.raw <- data.raw[, -c(3,4)]
  print(data.raw[, 2])
  data <- matrix(data.raw[,2], ncol=folds)
  x <- matrix(data.raw[,1], ncol=folds)

  print(data)
  plot_conv(data, ylim, 1, log(x[,1]), title, "VW Score","Log Examples",FALSE)
  dev.off()
}

read_file <- function(name) {
  file <- paste(elogs, name, sep='')
  data.raw <- read.csv(file=file, header=FALSE, sep=' ')
  data <- matrix(data.raw[,1], ncol=folds)
  
  return(data)
}

foldsizes <- read.table(paste(elogs, "twenty_testfolds_sizes.dat", sep=''), header=FALSE)
folds <- length(foldsizes[,1])

dca_plot(read_file("dca_eval_twenty_newsgroups_noopt.log.dat"), "dca_conv_20_noopt.pdf", c(12.6,12.9), "DCA", TRUE)
dca_plot(read_file("dca_eval_twenty_newsgroups_opt.log.dat"), "dca_conv_20_opt.pdf", c(11.85,12.2), "DCA (Hyperparameters optimized)")

mallet_plot(read_file("mallet_eval_twenty_newsgroups_noopt.log.dat"), "mallet_conv_20_noopt.pdf", c(8.7,8.85), "Mallet",TRUE)
mallet_plot(read_file("mallet_eval_twenty_newsgroups_noopt_check.log.dat"), "mallet_conv_20_noopt_check.pdf", c(8.7,8.85), "Mallet")
mallet_plot(read_file("mallet_eval_twenty_newsgroups_noopt2.log.dat"), "mallet_conv_20_noopt2.pdf", c(8.7,8.85), "Mallet")

mallet_plot(read_file("mallet_eval_twenty_newsgroups_opt.log.dat"), "mallet_conv_20_opt.pdf", c(8.2,8.85), "Mallet (Hyperparameters optimized)")

plda_plot(read_file("plda_eval_twenty_newsgroups_noopt.log.dat"), "plda_conv_20_noopt.pdf", c(60,75), "PLDA")

vw_plot("vw_eval_twenty_newsgroups_noopt.log.dat", "vw_conv_20_noopt.pdf", c(8.4,12.2), "VW")


##### PUBMED #####
foldsizes <- read.table(paste(elogs, "pubmed_testfolds_sizes.dat", sep=''), header=FALSE)
folds <- length(foldsizes[,1])

dca_plot(read_file("dca_eval_pubmed_opt.log.dat"), "dca_conv_pubmed_opt.pdf", c(12.5,12.8), "DCA (Hyperparameters optimized)")
mallet_plot(read_file("mallet_eval_pubmed_opt.log.dat"), "mallet_conv_pubmed_opt.pdf", c(8.6,8.8), "Mallet (Hyperparameters optimized)")
plda_plot(read_file("plda_eval_pubmed_noopt.log.dat"), "plda_conv_pubmed_noopt.pdf", c(69,74), "PLDA")
vw_plot("vw_eval_pubmed_noopt.log.dat", "vw_conv_pubmed_noopt.pdf", c(8.4,14), "VW")
