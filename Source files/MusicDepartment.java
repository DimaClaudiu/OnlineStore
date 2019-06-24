package Department;

import List.Visitor;

public class MusicDepartment extends Department
{
    public MusicDepartment(String name, int ID)
    {
        super(name, ID);
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}
