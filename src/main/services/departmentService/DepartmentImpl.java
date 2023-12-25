package main.services.departmentService;

import main.models.Department;
import main.models.Hospital;
import main.services.generics.GenericChecks;
import main.services.myExceptions.NotFound;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepartmentImpl implements DepartmentInterface {
    final DepartmentDao departmentDao;
    private static Long id = 0L;

    public DepartmentImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
        return departmentDao.hospitalDao.getAllHospitals().stream().filter(e -> e.getId().equals(id)).findFirst().orElseThrow(() -> new NotFound("Not found")).getDepartments();
    }

    @Override
    public Department findDepartmentByName(String name) {
            return departmentDao.hospitalDao.getAllHospitals().stream()
                    .flatMap(hospital -> hospital.getDepartments().stream())
                    .filter(department -> department.getDepartmentName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElseThrow(() -> new NotFound("Department not found"));
    }

    @Override
    public String add(Long hospitalId, Department department) {
        departmentDao.hospitalDao.getAllHospitals().stream().filter(e -> e.getId().equals(hospitalId)).findFirst().orElseThrow(() -> new NotFound("Not found"));
        while (true) {
            System.out.println("Write name: ");
            String name = new Scanner(System.in).nextLine();
            if (GenericChecks.chechUnicalName(departmentDao.hospitalDao.getAllHospitals(), name)) {
                department.setId(++DepartmentImpl.id);
                department.setDepartmentName(name);
                departmentDao.hospitalDao.getAllHospitals().stream().filter(e -> e.getId().equals(hospitalId)).findFirst().get().getDepartments().add(department);
                return "Succeess";
            }
        }
    }

    @Override
    public void removeById(Long id) {
        departmentDao.hospitalDao.getAllHospitals().stream().flatMap(hospital -> hospital.getDepartments().stream().filter(department -> department.getId().equals(id))).findFirst().orElseThrow(() -> new NotFound("Not found"));
        departmentDao.hospitalDao.getAllHospitals().forEach(hospital1 -> {
            List<Department> updateDep = hospital1.getDepartments()
                    .stream()
                    .filter(department -> !department.getId().equals(id))
                    .collect(Collectors.toList());
            hospital1.setDepartments(updateDep);
        });
        System.out.println("Success");
    }

    @Override
    public String updateById(Long id, Department department) {
        departmentDao.hospitalDao.getAllHospitals().stream().flatMap(hospital -> hospital.getDepartments().stream().filter(department1 -> department1.getId().equals(id))).findFirst().orElseThrow(() -> new NotFound("Not found"));
        while (true) {
            System.out.println("Write name: ");
            String name = new Scanner(System.in).nextLine();
            if (GenericChecks.checforName(name)) {
                department.setDepartmentName(name);
                departmentDao.hospitalDao.getAllHospitals().stream().flatMap(hospital -> hospital.getDepartments().stream().filter(department1 -> department1.getId().equals(id))).findFirst().get().setDepartmentName(department.getDepartmentName());
                break;
            }
        }
        return "Success";
    }
}
