package main.services.doctorService;

import main.models.Doctor;
import main.services.generics.GenericService;

import java.util.List;

public interface DoctorInterface extends GenericService<Doctor> {
    Doctor findDoctorById(Long id);
    String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId);
    List<Doctor> getAllDoctorsByHospitalId(Long id);
    List<Doctor> getAllDoctorsByDepartmentId(Long id);
}
