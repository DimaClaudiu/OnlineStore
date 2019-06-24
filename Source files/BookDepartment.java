package Department;

import List.Visitor;

public class BookDepartment extends Department
{
    public BookDepartment(String name, int ID)
    {
        super(name, ID);
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}
