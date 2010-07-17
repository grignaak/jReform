package org.jreform.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jreform.Criterion;
import org.jreform.InputControl;
import org.jreform.InputDataType;
import org.jreform.util.ParsedValue;

/**
 * Base class for single- and multi-value input controls.
 * Validation rules are implemented by subclasses.
 * 
 * @author armandino (at) gmail.com
 * @author michael.deardeuff (at) gmail.com
 */
public abstract class AbstractInputControl<T> implements InputControl<T>
{
    private InputDataType<T> type;
    private String name;
    private boolean isRequired;
    private List<Criterion<T>> criteria = new ArrayList<Criterion<T>>();
    
    private Set<String> errors = new LinkedHashSet<String>();
    private String customErrorMessage;
    
    /**
     * Constructor.
     * 
     * @param type of this input's data.
     * @param name of this input.
     * @param isRequired is this a required or optional input field.
     * @param criteria this input's data must satisfy.
     */
    protected AbstractInputControl(InputDataType<T> type, String name)
    {
        this.type = type;
        this.name = name;
        this.isRequired = true;
    }
    
    public final InputDataType<T> getType()
    {
        return type;
    }
    
    public final String getInputName()
    {
        return name;
    }
    
    public final void setOnError(String message)
    {
        customErrorMessage = message;
    }
    
    public final void addCriterion(Criterion<T> criterion)
    {
        criteria.add(criterion);
    }
    
    public final boolean isRequired()
    {
        return isRequired;
    }
    
    public void setRequired(boolean isRequired)
    {
        this.isRequired = isRequired;
    }
    
    public final boolean isValid()
    {
        return errors.isEmpty();
    }

    protected Set<String> getCriteriaErrors(ParsedValue<T> parsedValue)
    {
        if(parsedValue.isNotParsed())
            return Collections.singleton(parsedValue.getError());
        
        Set<String> errors = new LinkedHashSet<String>();
        for(Criterion<T> criterion : criteria)
        {
            if (!criterion.isSatisfied(parsedValue.getValue()))
            {
                errors.add(criterion.getOnError());
            }
        }
        
        return errors;
    }
    

    public Set<String> getErrors()
    {
        return Collections.unmodifiableSet(errors);
    }

    protected void addError(String error)
    {
        if (customErrorMessage == null)
            errors.add(error);
        else
            errors.add(customErrorMessage);
    }

    protected void addErrors(Collection<String> errors)
    {
        for (String error : errors)
        {
            addError(error);
        }
    }
    
}
