package org.jreform.inputs;

import org.jreform.Criterion;
import org.jreform.InputDataType;

/**
 * @author armandino (at) gmail.com
 */
// TODO does this offer any functionality over BasicInput? I trow not!
public class Select<T> extends BasicInput<T>
{   
    public Select(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);
    }
}