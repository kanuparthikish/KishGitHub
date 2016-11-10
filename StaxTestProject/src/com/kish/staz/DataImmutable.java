package com.kish.staz;

import java.util.Date;

public class DataImmutable
{

    public DataImmutable(String name,Double salary,Date jDate)
    {
        this.name=name;
        this.salary=salary;
        this.jDate=jDate;
    }
    private final String name;
    private final Double salary;
    private final Date jDate;
    public String getName()
    {
        return name;
    }
    public Double getSalary()
    {
        return salary;
    }
    public Date getjDate()
    {
        return new Date(jDate.getTime());
    }
            
}
