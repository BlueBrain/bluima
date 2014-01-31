a <- commandArgs(trailingOnly=TRUE)
file <- a[1]
file_test <- a[2]
step <- as.numeric(a[3])

train <- read.csv(file=file, header=FALSE, sep=' ')
test <- read.csv(file=file_test, header=FALSE, sep=' ')
x_train<- 5*(1:length(train$V1))
x_test <- step*(1:length(test$V1))
length(test[[1]])
x_test
if (!interactive()) pdf("dca_convergence.pdf")
plot(x_train, log(train$V1), type="l", col="red",
     xlab="X-Axis",ylab="Y-Axis",main="DCA Convergence", ylim=range(c(2, 3)))
lines(x_test, log(test$V1), col="green")
#lines(train$V3, col="blue")
if (!interactive()) dev.off()
