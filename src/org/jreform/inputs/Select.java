package org.jreform.inputs;

import org.jreform.InputDataType;

/**
 * @author armandino (at) gmail.com
 */
// TODO does this offer any functionality over BasicInput? I trow not!
public class Select<T> extends BasicInput<T>
{   
    public Select(InputDataType<T> type, String name)
    {
        super(type, name);
    }
}