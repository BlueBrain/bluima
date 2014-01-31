#! /usr/bin/R --vanilla -q -f

source("/home/mz/Documents/epfl/projet/scripts/common.R")

plot_likelihoods <- function(files, desc, foldsizes, yrange, outfile) {
  d <- lapply(files, function(x) read.table(x, header = FALSE))
  print(length(unlist(d)))
  folds <- length(foldsizes[,1]) #length(d[[1]][,1])
  d.frame <- data.frame(matrix(unlist(d), nrow=folds))
  
  # Normalize
  d.norm <- apply(d.frame, 2, function(x) { x/foldsizes[,1]} )
  print(d.norm)
  med <- apply(d.norm, 2, median)
  if(folds != 10)
    stop("folds is not 10. Need to adjust parameters for confidence interval!")
  CI.dn <- apply(d.norm, 2, function(x) { tmp <- sort(x); tmp[ folds -1 ] })
  CI.up <- apply(d.norm, 2, function(x) { tmp <- sort(x); tmp[ 1 ] })
  
  pdf(paste(figs, outfile, sep=""))
  
  # Setup and display plot
  y <- med
  print("ok")
  print(med)
  print(CI.dn)
  print(CI.up)
  x <- 2*c(1:length(files))
  #CI.dn <- c(pmin,m - error)
  #CI.up <- c(pmax,m + error)
  plot(y ~ x, xlim=c(0,2*length(files))+1, ylim=yrange, xlab="", xaxt="n",
       ylab="Median Log Likelihood/Token",main="")
  
  axis(1, at = x, labels = desc)
  arrows(x,CI.dn,x,CI.up,code=3,length=0.2,angle=90)  
  
  dev.off()
}

files <- c(paste(elogs,"dca_eval_twenty_newsgroups_noopt.log.test.dat",
                          sep=''),
           paste(elogs,"dca_eval_twenty_newsgroups_noopt_time.log.test.dat",
                 sep=''),
           paste(elogs,
                 "mallet_eval_twenty_newsgroups_noopt.log.test.dat", sep=''),
           paste(elogs,
                 "mallet_eval_twenty_newsgroups_noopt_check.log.test.dat", sep=''),
           paste(elogs,
                 "mallet_eval_twenty_newsgroups_noopt_time.log.test.dat", sep=''),
           paste(elogs, "plda_eval_twenty_newsgroups_noopt.log.test.dat", sep=''),
           paste(elogs,
                 "vw_eval_twenty_newsgroups_noopt.log.test.dat", sep=''),
           paste(elogs,"dca_eval_twenty_newsgroups_opt.log.test.dat",
                 sep=''),
           paste(elogs,
                 "mallet_eval_twenty_newsgroups_opt.log.test.dat", sep=''))


desc <- c("D", "D ES", "M", "M 2", "M ES", "PLDA", "VW", "D H",  "M H")
foldsizes <- read.table(paste(elogs, "twenty_testfolds_sizes.dat", sep=''), header=FALSE)
plot_likelihoods(files, desc, foldsizes, c(-7, -9), "likelihood_comp_20.pdf")

files <- c(paste(elogs,"dca_eval_pubmed_opt.log.test.dat",
                 sep=''),
           paste(elogs,"mallet_eval_pubmed_opt.log.test.dat",
                 sep=''),
           paste(elogs,
                 "plda_eval_pubmed_noopt.log.test.dat", sep=''),
           paste(elogs,
                 "vw_eval_pubmed_noopt.log.test.dat", sep=''))
desc <- c("DCA Hyp. Opt", "Mallet Hyp. Opt", "PLDA", "VW")
foldsizes <- read.table(paste(elogs, "pubmed_testfolds_sizes.dat", sep=''), header=FALSE)
plot_likelihoods(files, desc, foldsizes, c(-7.9, -8.7), "likelihood_comp_pubmed.pdf")


