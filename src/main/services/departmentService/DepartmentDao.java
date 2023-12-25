package main.services.departmentService;

import main.models.Department;
import main.models.Hospital;
import main.services.hospitalService.HospitalDao;

import java.util.List;

public class DepartmentDao {
    final HospitalDao hospitalDao;

    public DepartmentDao(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    public boolean addDepartmentToHospital(int indexHospital, Department department) {
        List<Department> departments = hospitalDao.getAllHospitals().get(indexHospital).getDepartments();
        departments.add(department);
        hospitalDao.getAllHospitals().get(indexHospital).setDepartments(departments);
        return true;
    }

    public List<Department> getAllDdepartmentsInHospital(Hospital hospital) {
        return hospital.getDepartments();
    }

    public boolean removeDepartment(int indexHospital, int indexDepartment) {
        hospitalDao.getAllHospitals().get(indexHospital).getDepartments().remove(indexDepartment);
        return true;
    }
}
