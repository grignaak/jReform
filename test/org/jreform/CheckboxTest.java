package org.jreform;

import org.jreform.impl.HtmlFormSupport;
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
        
        // TODO check parsing?
        assertFalse(form.subscribe().isSelected());
        
        assertTrue(form.choices().getCheckedKeys().contains(choiceOne));
        assertTrue(form.choices().getCheckedKeys().contains(choiceTwo));
        
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
        
        
        assertTrue(form.optionalChoices().getCheckedKeys().contains(optChoiceTwo.toString()));
        assertTrue(form.optionalChoices().getCheckedKeys().contains(optChoiceOne.toString()));
        assertFalse(form.optionalChoices().getCheckedKeys().contains("doesntExist"));
    }
    
    public void testSetValueAttributeSetsState()
    {
        form.subscribe().setValueAttribute("value");
        assertTrue(form.subscribe().isSelected());
        
        form.choices().setValueAttributes(new String[] {"one", "two"});
        assertTrue(form.choices().getCheckedKeys().contains("one"));
        assertTrue(form.choices().getCheckedKeys().contains("two"));
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
