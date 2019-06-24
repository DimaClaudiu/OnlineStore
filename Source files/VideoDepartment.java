package Department;

import List.Visitor;

public class VideoDepartment extends Department
{
    public VideoDepartment(String name, int ID)
    {
        super(name, ID);
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}
