package org.jreform;

import static org.jreform.inputs.CheckableState.CHECKED;
import static org.jreform.inputs.CheckableState.UNCHECKED;

import org.jreform.impl.HtmlFormSupport;
import org.jreform.inputs.CheckableState;
import org.jreform.inputs.Checkbox;
import org.jreform.inputs.MultiCheckbox;

public class CheckboxTest extends BaseTestCase
{
    private static final String SUBSCRIBE = "subscribe";
    private static final String CHOICES = "choices";
    private static final String OPT_CHOICES = "optionalChoices";
    
    private TestForm form;
    
    protected void init()
    {
        form = new TestForm();
    }
    
    protected HtmlFormSupport getForm()
    {
        return form;
    }
    
    /** single value checkbox can only be optional and is always valid */
    public void testSingleValueCheckboxIsAlwaysOptionalAndValid()
    {
        final String choiceOne = "choiceOne";
        final String choiceTwo = "choiceTwo";
        
        // set required input
        setParameters(CHOICES, new String[] {choiceOne, choiceTwo});
        
        assertTrue(validateForm());
        
        assertFalse("Can't be required", form.subscribe().isRequired());
        assertTrue("Always valid", form.subscribe().isValid());
        
        assertTrue(form.subscribe().getValueAttribute().equals(""));
        
        assertTrue(form.subscribe().getState() == UNCHECKED);
        
        assertTrue(form.choices().getState().get(choiceOne) == CHECKED);
        assertTrue(form.choices().getState().get(choiceTwo) == CHECKED);
        
        assertTrue(form.choices().getValues().size() == 2);
        
        assertEquals(form.choices().getValues().get(0), choiceOne);
        assertEquals(form.choices().getValues().get(1), choiceTwo);
    }
    
    /** validation fails if required multi checkbox is not checked */
    public void testMulticheckboxIsRequired()
    {
        assertFalse(validateForm());
        
        assertTrue(form.choices().isRequired());
        assertFalse(form.choices().isValid());
        
        // TODO have this throw
        assertTrue(form.choices().getValues().isEmpty());
        assertTrue(form.choices().getValueAttributes().length == 0);
    }
    
    public void testOptionalMultiCheckbox()
    {
        Integer optChoiceOne = 10;
        Integer optChoiceTwo = 99;
        
        setParameters(CHOICES, new String[] {"required choices"});
        setParameters(OPT_CHOICES, new String[] {
                optChoiceOne.toString(),
                optChoiceTwo.toString()});
        
        assertTrue(validateForm());
        
        assertTrue(form.optionalChoices().getState().get(optChoiceOne.toString()) == CHECKED);
        assertTrue(form.optionalChoices().getState().get(optChoiceTwo.toString()) == CHECKED);
        assertTrue(form.optionalChoices().getState().get("doesntExist") == UNCHECKED);
    }
    
    public void testSetValueAttributeSetsState()
    {
        form.subscribe().setValueAttribute("value");
        assertEquals(CheckableState.CHECKED, form.subscribe().getState());
        
        form.choices().setValueAttributes(new String[] {"one", "two"});
        assertEquals(CheckableState.CHECKED, form.choices().getState().get("one"));
        assertEquals(CheckableState.CHECKED, form.choices().getState().get("two"));
    }

    private static class TestForm extends HtmlFormSupport
    {
        public TestForm()
        {
            // always optional
            checkbox(stringType(), SUBSCRIBE);
            
            // required multi checkbox
            multiCheckbox(stringType(), CHOICES);
            
            // optional multi checkbox
            multiCheckbox(intType(), OPT_CHOICES).optional();
        }
        
        public Checkbox<String> subscribe()
        {
            return getCheckbox(SUBSCRIBE);
        }
        
        public MultiCheckbox<String> choices()
        {
            return getMultiCheckbox(CHOICES);
        }
        
        public MultiCheckbox<Integer> optionalChoices()
        {
            return getMultiCheckbox(OPT_CHOICES);
        }
        
    }
    
}
