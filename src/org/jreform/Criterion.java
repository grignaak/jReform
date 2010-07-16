package org.jreform;

/**
 * A criterion that can evaluate to <code>true</code> or <code>false</code>.
 * 
 * @author armandino (at) gmail.com
 */
public interface Criterion<T>
{
    /**
     * Tests whether the specified value satisfies this criterion.
     * @param value to be tested against this criterion.
     * @return <code>true</code> if the criterion is satisfied,
     *         <code>false</code> otherwise.
     */
    public boolean isSatisfied(T value);

    /**
     * Returns a message describing an error or an empty
     * string if there was no error. 
     */
    public String getOnError();
}
