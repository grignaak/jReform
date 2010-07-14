package org.jreform.impl;

import javax.servlet.http.HttpServletRequest;

import org.jreform.Criterion;
import org.jreform.InputControl;
import org.jreform.InputDataType;

/**
 * Base class for single- and multi-value input controls.
 * Validation rules are implemented by subclasses.
 * 
 * @author armandino (at) gmail.com
 */
public abstract class AbstractInputControl<T> implements InputControl<T>
{
    private InputDataType<T> type;
    private String name;
    private String messageOnError;
    private boolean isRequired;
    private boolean isValid;
    private Criterion<T>[] criteria;
    
    /**
     * Constructor.
     * 
     * @param type of this input's data.
     * @param name of this input.
     * @param isRequired is this a required or optional input field.
     * @param criteria this input's data must satisfy.
     */
    protected AbstractInputControl(InputDataType<T> type, String name, Criterion<T>...criteria)
    {
        this.type = type;
        this.name = name;
        this.criteria = criteria;
        this.isRequired = true;
        this.isValid = true;
    }
    
    /**
     * Validates this input's <tt>value</tt> attribute(s). 
     */
    public abstract boolean validateRequest(HttpServletRequest req);
    
    public final InputDataType<T> getType()
    {
        return type;
    }
    
    public final String getInputName()
    {
        return name;
    }
    
    public final String getOnError()
    {
        return isValid() ? "" : messageOnError;
    }
    
    public final void setOnError(String message)
    {
        if(messageOnError == null)
            messageOnError = message;
    }
    
    public final boolean isRequired()
    {
        return isRequired;
    }
    
    protected final void setRequired(boolean isRequired)
    {
        this.isRequired = isRequired;
    }
    
    public final boolean isValid()
    {
        return isValid;
    }
    
    protected final void setValid(boolean isValid)
    {
        this.isValid = isValid;
    }
    
    protected final Criterion<T>[] getCriteria()
    {
        return criteria;
    }
    
}
