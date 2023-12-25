package main.services.departmentService;

import main.models.Department;
import main.services.generics.GenericService;

import java.util.List;

public interface DepartmentInterface extends GenericService<Department> {
    List<Department> getAllDepartmentByHospital(Long id);
    Department findDepartmentByName(String name);
}
