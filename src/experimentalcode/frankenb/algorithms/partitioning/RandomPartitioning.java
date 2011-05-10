package experimentalcode.frankenb.algorithms.partitioning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import de.lmu.ifi.dbs.elki.database.ids.ArrayModifiableDBIDs;
import de.lmu.ifi.dbs.elki.database.ids.DBID;
import de.lmu.ifi.dbs.elki.database.ids.DBIDUtil;
import de.lmu.ifi.dbs.elki.logging.Logging;
import de.lmu.ifi.dbs.elki.logging.LoggingConfiguration;
import de.lmu.ifi.dbs.elki.utilities.exceptions.UnableToComplyException;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameterization.Parameterization;
import experimentalcode.frankenb.model.BufferedDiskBackedPartition;
import experimentalcode.frankenb.model.ifaces.IDataSet;
import experimentalcode.frankenb.model.ifaces.IPartition;

/**
 * This class divides the data into random partitions.
 * <p/>
 * Note that the original vectors are used in the distributed calculation.
 * 
 * @author Florian
 */
public class RandomPartitioning extends AbstractFixedAmountPartitioning {
  /**
   * Logger
   */
  private static final Logging logger = Logging.getLogger(RandomPartitioning.class);

  public RandomPartitioning(Parameterization config) {
    super(config);
    LoggingConfiguration.setLevelFor(RandomPartitioning.class.getCanonicalName(), Level.ALL.getName());
  }
  
  @Override
  public List<IPartition> makePartitions(IDataSet dataSet, int packageQuantity, int partitionQuantity) throws UnableToComplyException {
    try {
      int dataEntriesPerPartition = (int)Math.ceil(dataSet.getSize() / (float)partitionQuantity);
      
      getLogger().verbose("each random partition will contain about " + dataEntriesPerPartition + " items");
      
      Random random = new Random(System.currentTimeMillis());
      ArrayModifiableDBIDs candidates = DBIDUtil.newArray();
      for (DBID dbid : dataSet.getIDs()) {
        candidates.add(dbid);
      }
      
      List<IPartition> partitions = new ArrayList<IPartition>();
      for (int i = 0; i < partitionQuantity; ++i) {
        IPartition partition = new BufferedDiskBackedPartition(i, dataSet.getDimensionality());
        for (int j = 0; j < dataEntriesPerPartition; ++j) {
          if (candidates.size() == 0) break;
          DBID candidate = candidates.remove(random.nextInt(candidates.size()));
          partition.addVector(candidate, dataSet.getOriginal().get(candidate));
        }
        partitions.add(partition);
      }
      
      return partitions;
      
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new UnableToComplyException(e);
    }
    
  }

  @Override
  protected Logging getLogger() {
    return logger;
  }
}