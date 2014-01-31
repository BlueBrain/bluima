#!/bin/bash -l
#SBATCH --job-name="DCA Conv CV"
#SBATCH --nodes=1
#SBATCH --ntasks=4
#SBATCH --ntasks-per-node=4
#SBATCH --mem=4096
#SBATCH --time=23:30:00
#SBATCH --partition=batch
#SBATCH --mail-type=ALL
#SBATCH --mail-user=marc.zimmermann@epfl.ch
#SBATCH --output=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-stdout.log
#SBATCH --error=/nfs4/bbp.epfl.ch/user/mazimmer/slurm-stderr.log
 
# In case there are per-group custom initialization files
#. /nfs4/bbp.epfl.ch/group/visualization/module/modules.bash
# Load your required module files here
#module load MODULE_TO_BE_LOADED
 
# To avoid Kerberos tickets becoming expired, run the following in
# the background to check every 30min and try to renew it
krenew -b -K 30
 
echo "On which node your job has been scheduled :"
echo $SLURM_JOB_NODELIST
echo "Print current shell limits :"
ulimit -a

# Calculates models for different number of components

# Assumes that data has been prepared such that test and training sets
# of fold i have the filename test$i.txtbag and
# training$i.txtbag. This way, we just rename files and execute mpdata
# and mphier

documents=19971 # total number of documents in Corpus
folds=10 # number of folds
#testsize=1997

iterations=900
estimation_iterations=15

cd $HOME/private/corpora/twenty_newsgroups/cv/dca


folds_done=`cat foldscnt`

if [ $folds_done = ""]
then
	rm dca.err
fi

for((i=$folds_done + 1; i<folds; i+=1))
do
    echo "DOING FOLD $i"
    # prepare data
    cp dca.txtbag$i dca.txtbag
    mpdata dca
    testsize=`wc -l ../plda/plda_test$i | awk '{print $1}'` # hack
    echo "testdocs=$testsize" >> dca.par
    echo "alpha_update" >> dca.par 
    echo "components=42" >> dca.par # set some default value, will be
				# changed shortly

    for topics in {5,10,15,20,25,30,50,75,100,150,200}
    do
	echo "At ITERATION $topics"
	rm dca.alpha
	rm dca.gamma
	rm dca.struct
	rm -r dca.comps

	# adjust number of components
	sed -e "s/^components=\([0-9]*\)$/components=$topics/g" dca.par > /tmp/dca
	mv /tmp/dca dca.par
	
	mphier -L$iterations -e dca
	mphier -r -t 4 -X$estimation_iterations,M -e dca
    done

    echo "$i" > foldscnt
done

cp dca.err ../dca_20n_topics_cv_cluster.log
