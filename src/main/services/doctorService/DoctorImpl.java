package main.services.doctorService;

import main.models.Department;
import main.models.Doctor;
import main.models.Gender;
import main.models.Hospital;
import main.services.generics.GenericChecks;
import main.services.myExceptions.NotFound;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DoctorImpl implements DoctorInterface {
    private static long id = 0;
    final DoctorDao doctorDao;

    public DoctorImpl(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    @Override
    public Doctor findDoctorById(Long id) {
        return doctorDao.hospitalDao.getAllHospitals().stream().flatMap(hospital -> hospital.getDoctors().stream().filter(doctor -> doctor.getId().equals(id))).findFirst().orElseThrow(() -> new NotFound("Not fount"));
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        doctorDao.hospitalDao.getAllHospitals().stream().flatMap(hospital -> hospital.getDepartments().stream().filter(department -> department.getId().equals(departmentId))).findFirst().orElseThrow(() -> new NotFound("Not found"));
        List<Doctor> list = new ArrayList<>();
        for (Long id : doctorsId) {
            list = doctorDao.hospitalDao.getAllHospitals().stream().flatMap(hospital -> hospital.getDoctors().stream().filter(doctor -> doctor.getId().equals(id))).toList();
        }
        doctorDao.hospitalDao.getAllHospitals().stream()
                .flatMap(hospital -> hospital.getDepartments().stream()
                        .filter(department -> department.getId().equals(departmentId)))
                .findFirst().orElseThrow(() -> new NotFound("Not found"))
                .getDoctors().addAll(list);
        return "Suuccess";
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        return doctorDao.hospitalDao.getAllHospitals().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst().orElseThrow(() -> new NotFound("Not found"))
                .getDoctors();
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        return doctorDao.hospitalDao.getAllHospitals().stream()
                .flatMap(hospital -> hospital.getDepartments()
                        .stream()
                        .filter(department -> department.getId().equals(id)))
                .findFirst().orElseThrow(() -> new NotFound("Not found"))
                .getDoctors();

    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {
        doctorDao.hospitalDao.getAllHospitals().stream().filter(hospital -> hospital.getId().equals(hospitalId)).findFirst().orElseThrow(() -> new NotFound("Not found"));
        while (true) {
            System.out.println("Write first name: ");
            String name = new Scanner(System.in).nextLine();
            if (GenericChecks.checforName(name)) {
                doctor.setFirstName(name);
                doctor.setId(++DoctorImpl.id);
                break;
            }
        }
        while (true) {
            System.out.println("Write last name: ");
            String name = new Scanner(System.in).nextLine();
            if (GenericChecks.checforName(name)) {
                doctor.setLastName(name);
                break;
            }
        }
        while (true) {
            System.out.println("Write gender name: ");
            String name = new Scanner(System.in).nextLine();
            if (name.equalsIgnoreCase(Gender.MALE.name())) {
                doctor.setGender(Gender.MALE);
                break;
            } else if (name.equalsIgnoreCase(Gender.FEMALE.name())) {
                doctor.setGender(Gender.FEMALE);
                break;
            }
        }
        while (true) {
            System.out.println("Write experience Year: ");
            try {
                int num = new Scanner(System.in).nextInt();
                if (num > 1) {
                    doctor.setExperienceYear(num);
                    break;
                }
            } catch (InputMismatchException exception) {
                System.out.println("Write currect number please");
            }
        }
        doctorDao.hospitalDao.getAllHospitals().stream().filter(hospital -> hospital.getId().equals(hospitalId)).findFirst().orElseThrow(() -> new NotFound("Not found")).getDoctors().add(doctor);
        return "Success";
    }

    @Override
    public void removeById(Long id) {
        doctorDao.hospitalDao.getAllHospitals().stream().map(hospital -> hospital.getDoctors().removeIf(doctor -> doctor.getId().equals(id))).findFirst().orElseThrow(() -> new NotFound("Not found"));
        doctorDao.hospitalDao.getAllHospitals().stream().map(hospital -> hospital.getDepartments().stream().dropWhile(department -> department.getDoctors().removeIf(doctor -> doctor.getId().equals(id))).findFirst().orElseThrow(() -> new NotFound("Not found")));
        System.out.println("Success");
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        List<Hospital> hospitals = doctorDao.hospitalDao.getAllHospitals();

        int indexHospital = IntStream.range(0, hospitals.size())
                .filter(i -> hospitals.get(i).getDoctors().stream()
                        .anyMatch(doctor1 -> doctor1.getId().equals(id)))
                .findFirst()
                .orElse(-1);

        int indexDoctor = (indexHospital != -1) ?
                IntStream.range(0, hospitals.get(indexHospital).getDoctors().size())
                        .filter(i -> hospitals.get(indexHospital).getDoctors().get(i).getId().equals(id))
                        .findFirst()
                        .orElse(-1)
                : -1;
        int indexDepartmentInHospital = (indexHospital != -1 && indexDoctor != -1) ?
                IntStream.range(0, hospitals.get(indexHospital).getDepartments().size())
                        .filter(i -> hospitals.get(indexHospital).getDepartments().get(i).getDoctors().stream()
                                .anyMatch(doctor2 -> doctor2.getId().equals(id)))
                        .findFirst()
                        .orElse(-1) :
                -1;
        int indexDoctorinDepartment = -1;
        for (int i = 0; i < hospitals.get(indexHospital).getDepartments().get(indexDepartmentInHospital).getDoctors().size(); i++) {
            if (hospitals.get(indexHospital).getDepartments().get(indexDepartmentInHospital).getDoctors().get(i).getId().equals(id)){
                indexDoctorinDepartment = i;
            }
        }
        if (indexDoctorinDepartment!=-1){
            System.out.println("""
                Choose comaand fir update:
                1.First name
                2.Last name
                3.Gender
                4.Experience year
                                
                """);
            try {
                int action = new Scanner(System.in).nextInt();
                switch (action) {
                    case 1 -> {
                        while (true) {
                            System.out.println("Write first name:");
                            String name = new Scanner(System.in).nextLine();
                            if (GenericChecks.checforName(name)) {
                                doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDoctors().get(indexDoctor).setFirstName(name);
                                doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDepartments().get(indexDepartmentInHospital).getDoctors().get(indexDoctorinDepartment).setFirstName(name);
                                return "Success";
                            }
                        }
                    }
                    case 2 -> {
                        while (true) {
                            System.out.println("Write last name: ");
                            String name = new Scanner(System.in).nextLine();
                            if (GenericChecks.checforName(name)) {
                                doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDoctors().get(indexDoctor).setLastName(name);
                                doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDepartments().get(indexDepartmentInHospital).getDoctors().get(indexDoctorinDepartment).setLastName(name);
                                return "Success";
                            }
                        }
                    }
                    case 3 -> {
                        while (true) {
                            System.out.println("Write gender: ");
                            String gender = new Scanner(System.in).nextLine();
                            if (gender.equalsIgnoreCase(Gender.MALE.name())) {
                                doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDoctors().get(indexDoctor).setGender(Gender.MALE);
                                doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDepartments().get(indexDepartmentInHospital).getDoctors().get(indexDoctorinDepartment).setGender(Gender.MALE);
                                return "Success";
                            } else if (gender.equalsIgnoreCase(Gender.FEMALE.name())) {
                                doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDoctors().get(indexDoctor).setGender(Gender.FEMALE);
                                doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDepartments().get(indexDepartmentInHospital).getDoctors().get(indexDoctorinDepartment).setGender(Gender.FEMALE);
                                return "Success";
                            }
                        }
                    }
                    case 4 -> {
                        while (true) {
                            System.out.println("Write exeption year: ");
                            try {
                                int year = new Scanner(System.in).nextInt();
                                if (year > 1) {
                                    doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDoctors().get(indexDoctor).setExperienceYear(year);
                                    doctorDao.hospitalDao.getAllHospitals().get(indexHospital).getDepartments().get(indexDepartmentInHospital).getDoctors().get(indexDoctorinDepartment).setExperienceYear(year);
                                    return "Success";
                                }
                            } catch (InputMismatchException exception) {
                                System.out.println("Write only numbers please!");
                            }
                        }
                    }
                }
            } catch (InputMismatchException exception) {
                System.out.println("Write only numbers please!");
            }
        } throw new NotFound("Not found");
    }

}
