package de.lmu.ifi.dbs.elki.distance;

import de.lmu.ifi.dbs.elki.logging.AbstractLoggable;
import de.lmu.ifi.dbs.elki.logging.LoggingConfiguration;

/**
 * An abstract distance implements equals conveniently for any extending class.
 * At the same time any extending class is to implement hashCode properly.
 *
 * @author Arthur Zimek
 * @param <D> the type of Distance used
 */
abstract class AbstractDistance<D extends AbstractDistance<D>> extends AbstractLoggable implements Distance<D> {

    /**
     * Sets as debug status
     * {@link LoggingConfiguration#DEBUG}.
     */
    protected AbstractDistance() {
        super(LoggingConfiguration.DEBUG);
    }

    /**
     * Any extending class should implement a proper hashCode method.
     *
     * @see Object#hashCode()
     */
    public abstract int hashCode();

    /**
     * Returns true if o is of the same class as this instance
     * and <code>this.compareTo(o)</code> is 0,
     * false otherwise.
     *
     * @see Object#equals(Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;


        try {
            return this.compareTo((D) o) == 0;
        }
        catch (ClassCastException e) {
            return false;
        }
    }
}
