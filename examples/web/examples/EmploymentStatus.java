package web.examples;

/**
 * This enum type is used by {@link EmploymentStatusDataType} as an example
 * of implementing a custom InputDataType.
 */
public enum EmploymentStatus
{
    EMPLOYED(1, "Employed"),
    SELF_EMPLOYED(2, "Self-employed"),
    UNEMPLOYED(3, "Unemplyed"),
    RETIRED(4, "Retired"),
    STUDENT(5, "Student");
    
    private int id;
    private String desc;
    
    EmploymentStatus(int id, String desc)
    {
        this.id = id;
        this.desc = desc;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public static EmploymentStatus getByID(int id)
    {
        for(EmploymentStatus status : values())
            if(status.getId() == id)
                return status;
        
        throw new IllegalArgumentException("Invalid id: " + id);
    }
    
    
}
