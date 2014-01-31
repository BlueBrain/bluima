#!/bin/bash -l
#SBATCH --job-name="Pubmed CV"
#SBATCH --nodes=1
#SBATCH --ntasks=11
#SBATCH --ntasks-per-node=11
#SBATCH --mem=7096
#SBATCH --time=23:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=renaud.richardet@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/richarde/slurm-pubmed-cv-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/richarde/slurm-pubmed-cv-stderr.log
 
# To avoid Kerberos tickets becoming expired, run the following in
# the background to check every 30min and try to renew it
krenew -b -K 30
 

# Calculates models for different number of components

# Assumes that data has been prepared such that test and training sets
# of fold i have the filename test$i.txtbag and
# training$i.txtbag. This way, we just rename files and execute mpdata
# and mphier

iterations=550
avg_after=520
est_cycles=25

estimator_path="$HOME/private/topic_models"
corpus="$HOME/private/corpora/pubmed_cv"
working_dir="$corpus/dca"
cd $working_dir

i=0
logfile="$HOME/private/logs/pubmed_cv_fold$i.log"
rm $logfile

echo "DOING FOLD $i" >> $logfile

# prepare data
cp dca.txtbag$i dca.txtbag
mpdata dca >> $logfile 2>&1
echo "alpha_update" >> dca.par 
echo "components=42" >> dca.par # set some default value, will be
				# changed shortly
for topics in {50,500,400,300,250,200,100}
do
	echo "Working with $topics topics" >> $logfile
	rm dca.alpha
	rm dca.gamma
	rm dca.struct
	rm -r dca.comps

	# adjust number of components
	sed -e "s/^components=\([0-9]*\)$/components=$topics/g" dca.par > /tmp/dca
	mv /tmp/dca dca.par
	
	mphier -t 11 -L$iterations -A$avg_after -d2000 -i 10000000 dca >> $logfile 2>&1

	corpus_size=$(wc -w "../training$i.txt" | awk '{print $1}')
	mpupd -T -t 1,1 dca | sed 's/Component [0-9]* (\([0-9.]*\)):/\1/g' > dca.topics

	cp -r $working_dir "$corpus/dca$topics"
	echo "corpus size: $corpus_size" >> $logfile

	cd $estimator_path
	sbt "run-main ch.epfl.bbp.uima.topicmodels.estimator.DCAModelEstimation -t $corpus_size -c $est_cycles $working_dir dca $corpus/test$i.txt" >> $logfile
	cd $working_dir
done

