#!/bin/bash -l
#SBATCH --job-name="DCA Conv CV"
#SBATCH --nodes=1
#SBATCH --ntasks=2
#SBATCH --ntasks-per-node=2
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

# Does crossvalidation over DCA corpus
# Assumes that data has been prepared such that test and training sets
# of fold i have the filename test$i.txtbag and
# training$i.txtbag. This way, we just rename files and execute mpdata
# and mphier

documents=19971 # total number of documents in Corpus
folds=10 # number of folds
#testsize=1997

iterations=700
estimation_iterations=10
step=20

cd $HOME/private/corpora/twenty_newsgroups/cv/dca # change to where dca files are
				# stored

rm dca.err

for((i=0; i<folds; i+=1))
do
 echo "Iteration $i"

 # cleanup 
 rm dca.alpha
 rm dca.gamma
 rm dca.struct
 rm -r dca.comps


 # prepare data
 cp dca.txtbag$i dca.txtbag
 mpdata dca
 echo "components=20" >> dca.par
 testsize=`wc -l ../plda/plda_test$i | awk '{print $1}'` # hack
 echo "testdocs=$testsize" >> dca.par
 echo "alpha_update" >> dca.par 

 # train and test data. Estimate test error every 10 iterations
 mphier -L$step -e dca
 mphier -r -t 2 -X$estimation_iterations,M dca 2> out.tmp
 grep Final out.tmp >> dca.err
 for (( c=$step+$step; c<=$iterations; c+=$step ))
 do
     echo "At step $c"
     mphier -r -L$step -e dca
     mphier -r -t 2 -X$estimation_iterations,M dca 2> out.tmp
     grep Final out.tmp >> dca.err
 done
done

cp dca.err ../dca_20n_convergence_cv_cluster.log

