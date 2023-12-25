package main.services.doctorService;

import main.models.Doctor;
import main.services.hospitalService.HospitalDao;

import java.util.List;

public class DoctorDao {
    final HospitalDao hospitalDao;

    public DoctorDao(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    public boolean addDoctorToHospital(int indexHospital, Doctor doctor) {
        List<Doctor> doctors = hospitalDao.getAllHospitals().get(indexHospital).getDoctors();
        doctors.add(doctor);
        hospitalDao.getAllHospitals().get(indexHospital).setDoctors(doctors);
        return true;
    }

    public List<Doctor> getAllsDoctorsInHosptal(int indexhospital) {
        return hospitalDao.getAllHospitals().get(indexhospital).getDoctors();
    }

    public boolean removeDoctorInHospital(int indexHospital, int indexDoctor) {
        hospitalDao.getAllHospitals().get(indexHospital).getPatients().remove(indexDoctor);
        return true;
    }
}
