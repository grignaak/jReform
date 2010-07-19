package org.jreform.inputs;

import org.jreform.InputDataType;

/**
 * @author armandino (at) gmail.com
 */
// TODO how does this offer any functionality over BasicInput?
// TODO add acceptable values
// TODO what happens if a radio is in a group?
public class Radio<T> extends BasicInput<T>
{
    public Radio(InputDataType<T> type, String name)
    {
        super(type, name);
    }
    
    @Override
    public void setRequired(boolean isRequired)
    {
        if (!isRequired)
            throw new IllegalArgumentException("Cannot make a radio optional");
        super.setRequired(isRequired);
    }
}
