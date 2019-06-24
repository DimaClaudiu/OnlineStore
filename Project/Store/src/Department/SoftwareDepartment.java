package Department;

import List.Visitor;

public class SoftwareDepartment extends Department
{
    public SoftwareDepartment(String name, int ID)
    {
        super(name, ID);
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}
