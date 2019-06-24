package List;

import Department.BookDepartment;
import Department.MusicDepartment;
import Department.SoftwareDepartment;
import Department.VideoDepartment;

public interface Visitor
{
    void visit(BookDepartment bookDepartment);

    void visit(MusicDepartment musicDepartment);

    void visit(SoftwareDepartment softwareDepartment);

    void visit(VideoDepartment videoDepartment);

}