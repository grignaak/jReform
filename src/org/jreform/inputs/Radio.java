package org.jreform.inputs;

import org.jreform.Criterion;
import org.jreform.InputDataType;

/**
 * @author armandino (at) gmail.com
 */
// TODO how does this offer any functionality over BasicInput?
// TODO add acceptable values
public class Radio<T> extends BasicInput<T>
{
    public Radio(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
    }
}
