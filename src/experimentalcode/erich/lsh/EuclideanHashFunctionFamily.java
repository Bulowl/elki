package experimentalcode.erich.lsh;

/*
 This file is part of ELKI:
 Environment for Developing KDD-Applications Supported by Index-Structures

 Copyright (C) 2013
 Ludwig-Maximilians-Universität München
 Lehr- und Forschungseinheit für Datenbanksysteme
 ELKI Development Team

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import de.lmu.ifi.dbs.elki.math.linearalgebra.randomprojections.GaussianRandomProjectionFamily;
import de.lmu.ifi.dbs.elki.utilities.RandomFactory;
import de.lmu.ifi.dbs.elki.utilities.documentation.Reference;

/**
 * 2-stable hash function family for Euclidean distances.
 * 
 * Reference:
 * <p>
 * Locality-sensitive hashing scheme based on p-stable distributions<br />
 * M. Datar and N. Immorlica and P. Indyk and V. S. Mirrokni<br />
 * Proc. 20th annual symposium on Computational geometry<br />
 * </p>
 * 
 * @author Erich Schubert
 */
@Reference(authors = "M. Datar and N. Immorlica and P. Indyk and V. S. Mirrokni", title = "Locality-sensitive hashing scheme based on p-stable distributions", booktitle = "Proc. 20th annual symposium on Computational geometry", url = "http://dx.doi.org/10.1145/997817.997857")
public class EuclideanHashFunctionFamily extends AbstractHashFunctionFamily {
  /**
   * Constructor.
   * 
   * @param random Random generator
   * @param width Bin width
   * @param l Number of projections to combine.
   */
  public EuclideanHashFunctionFamily(RandomFactory random, double width, int l) {
    super(random, new GaussianRandomProjectionFamily(random), width, l);
  }

  /**
   * Parameterization class.
   * 
   * @author Erich Schubert
   * 
   * @apiviz.exclude
   */
  public static class Parameterizer extends AbstractHashFunctionFamily.Parameterizer {
    @Override
    protected EuclideanHashFunctionFamily makeInstance() {
      return new EuclideanHashFunctionFamily(random, width, l);
    }
  }
}
