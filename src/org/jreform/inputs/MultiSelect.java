package org.jreform.inputs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jreform.InputDataType;

/**
 * @author armandino (at) gmail.com
 */
public class MultiSelect<T> extends BasicMultiInput<T>
{   
    public MultiSelect(InputDataType<T> type, String name)
    {
        super(type, name);
    }
    
    public Set<String> getSelectedKeys()
    {
        return new HashSet<String>(Arrays.asList(getValueAttributes()));
    }
    
}
