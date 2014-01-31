///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Jason Baldridge and Gann Bierner
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.maxent;

/**
 * A Factory class which uses instances of GISTrainer to create and train
 * GISModels.
 *
 * @author  Jason Baldridge
 * @version $Revision: 1.7 $, $Date: 2005/10/13 18:21:15 $
 */
public class GIS {
    /**
     * Set this to false if you don't want messages about the progress of
     * model training displayed. Alternately, you can use the overloaded
     * version of trainModel() to conditionally enable progress messages.
     */
    public static boolean PRINT_MESSAGES = true;

    /** If we are using smoothing, this is used as the "number" of
     * times we want the trainer to imagine that it saw a feature that it
     * actually didn't see.  Defaulted to 0.1.
     */
    public static double SMOOTHING_OBSERVATION = 0.1;
    
    /**
     * Train a model using the GIS algorithm, assuming 100 iterations and no
     * cutoff.
     *
     * @param eventStream The EventStream holding the data on which this model
     *                    will be trained.
     * @return The newly trained model, which can be used immediately or saved
     *         to disk using an opennlp.maxent.io.GISModelWriter object.
     */
    public static GISModel trainModel(EventStream eventStream) {
        return trainModel(eventStream, 100, 0, false, PRINT_MESSAGES);
    }
    
    /**
     * Train a model using the GIS algorithm, assuming 100 iterations and no
     * cutoff.
     *
     * @param eventStream The EventStream holding the data on which this model
     *                    will be trained.
     * @param smoothing   Defines whether the created trainer will use smoothing 
     *                    while training the model.
     * @return The newly trained model, which can be used immediately or saved
     *         to disk using an opennlp.maxent.io.GISModelWriter object.
     */
    public static GISModel trainModel(EventStream eventStream, boolean smoothing) {
      return trainModel(eventStream, 100, 0, smoothing,PRINT_MESSAGES);
  }

    /**
     * Train a model using the GIS algorithm.
     *
     * @param eventStream The EventStream holding the data on which this model
     *                    will be trained.
     * @param iterations  The number of GIS iterations to perform.
     * @param cutoff      The number of times a feature must be seen in order
     *                    to be relevant for training.
     * @return The newly trained model, which can be used immediately or saved
     *         to disk using an opennlp.maxent.io.GISModelWriter object.
     */
    public static GISModel trainModel(EventStream eventStream,
                                      int iterations,
                                      int cutoff) {
        return trainModel(eventStream, iterations, cutoff, false,PRINT_MESSAGES);
    }
    
     /**
     * Train a model using the GIS algorithm.
     * @param eventStream The EventStream holding the data on which this model
     *                    will be trained.
     * @param iterations  The number of GIS iterations to perform.
     * @param cutoff      The number of times a feature must be seen in order
     *                    to be relevant for training.
     * @param smoothing   Defines whether the created trainer will use smoothing 
     *                    while training the model.
     * @param printMessagesWhileTraining Determines whether training status messages are written to STDOUT. 
     * @return The newly trained model, which can be used immediately or saved
     *         to disk using an opennlp.maxent.io.GISModelWriter object.
     */
    public static GISModel trainModel(EventStream eventStream,
        int iterations,
        int cutoff,
        boolean smoothing,boolean printMessagesWhileTraining) {
      GISTrainer trainer = new GISTrainer(printMessagesWhileTraining);
      trainer.setSmoothing(smoothing);
      trainer.setSmoothingObservation(SMOOTHING_OBSERVATION);
      return trainer.trainModel(eventStream, iterations, cutoff);
    }
    
    /**
     * Train a model using the GIS algorithm.
     * @param iterations The number of GIS iterations to perform.
     * @param indexer The object which will be used for event compilation.
     * @param smoothing Defines whether the created trainer will use smoothing while training the model.
     * @return The newly trained model, which can be used immediately or saved
     *         to disk using an opennlp.maxent.io.GISModelWriter object.
     */
    public static GISModel trainModel(int iterations, DataIndexer indexer, boolean smoothing) {
      return trainModel(iterations,indexer,false,smoothing);
    }
    
    /**
     * Train a model using the GIS algorithm.
     * @param iterations The number of GIS iterations to perform.
     * @param indexer The object which will be used for event compilation.
     * @param printMessagesWhileTraining Determines whether training status messages are written to STDOUT.
     * @param smoothing Defines whether the created trainer will use smoothing while training the model.
     * @return The newly trained model, which can be used immediately or saved
     *         to disk using an opennlp.maxent.io.GISModelWriter object.
     */
    public static GISModel trainModel(int iterations, DataIndexer indexer, boolean printMessagesWhileTraining, boolean smoothing) {
      GISTrainer trainer = new GISTrainer(printMessagesWhileTraining);
      trainer.setSmoothing(smoothing);
      trainer.setSmoothingObservation(SMOOTHING_OBSERVATION);
      return trainer.trainModel(iterations, indexer);
    }
      
    /**
     * Train a model using the GIS algorithm.
     * @param iterations The number of GIS iterations to perform.
     * @param indexer The object which will be used for event compilation.
     * @return The newly trained model, which can be used immediately or saved
     *         to disk using an opennlp.maxent.io.GISModelWriter object.
     */
    public static GISModel trainModel(int iterations, DataIndexer indexer) {
           return trainModel(iterations,indexer,true,false);   
    }
  
}



