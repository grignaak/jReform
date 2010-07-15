package org.jreform.inputs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jreform.Criterion;
import org.jreform.InputDataType;

public class MultiCheckbox<T> extends BasicMultiInput<T>
{  
    public MultiCheckbox(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        super(type, name, criteria);   
    }
    
    // TODO better replaced by isChecked(key)
    public Set<String> getCheckedKeys()
    {
        return new HashSet<String>(Arrays.asList(getValueAttributes()));
    }
}
