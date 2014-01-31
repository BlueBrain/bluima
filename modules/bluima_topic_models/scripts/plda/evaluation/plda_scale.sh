#!/bin/bash -l

MPI_LDA="/home/mz/Documents/epfl/projet/plda/mpi_lda"

corpus_root="/home/mz/Documents/epfl/projet/corpora/pubmed"
topics=100
logfile="/home/mz/Documents/epfl/projet/data/scale/plda_eval_$(basename $corpus_root)_opt_time_local.log"

# Griffiths and Steyvers proposal
alpha=$(echo "scale=4;50/$topics" | bc)
beta=0.01

iterations=30

working_dir="$corpus_root/plda"
folds=10 # number of folds

cd $working_dir

rm $logfile

for threads in {1,2,3,4}
do
    i=0
    echo "Processing fold $i with $threads threads" >> $logfile
    date >> $logfile
    
    pwd

    startt=$(date "+%s")
    #it=$(echo "$iterations/$threads" | bc)
    it=$iterations
    echo "doing $it iterations" >> $logfile
  \time -o plda.usage -a -f "%e %D %K %M" mpiexec -n $threads $MPI_LDA --num_topics $topics --alpha $alpha --beta $beta \
             --training_data_file plda_train0 \
             --model_file lda_model.txt \
             --total_iterations $it >> $logfile

     endt=$(date "+%s")

    t=$(echo "$endt - $startt" | bc)
    echo "$threads $t" >> $logfile
done
