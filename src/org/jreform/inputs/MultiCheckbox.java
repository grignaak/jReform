package org.jreform.inputs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jreform.InputDataType;

public class MultiCheckbox<T> extends BasicMultiInput<T>
{  
    public MultiCheckbox(InputDataType<T> type, String name)
    {
        super(type, name);   
    }
    
    // TODO better replaced by isChecked(key)
    public Set<String> getCheckedKeys()
    {
        return new HashSet<String>(Arrays.asList(getValueAttributes()));
    }
}
