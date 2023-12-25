package main.services.patcientSerrvice;

import main.models.Gender;
import main.models.Hospital;
import main.models.Patcient;
import main.services.generics.GenericChecks;
import main.services.myExceptions.NotFound;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PatcientImpl implements PatcientService {
    public static Long Id = 0L;
    final PatcientDao patcientDao;

    public PatcientImpl(PatcientDao patcientDao) {
        this.patcientDao = patcientDao;
    }

    @Override
    public String add(Long hospitalId, Patcient patcient) {
        patcientDao.hospitalDao.getAllHospitals().stream().filter(hospital -> hospital.getId().equals(hospitalId)).findFirst().orElseThrow(() -> new NotFound("Not found"));
        while (true) {
            System.out.println("Write first name ");
            String name = new Scanner(System.in).nextLine();
            if (GenericChecks.checforName(name)) {
                patcient.setFirstName(name);
                patcient.setId(++PatcientImpl.Id);
                break;
            }
        }
        while (true) {
            System.out.println("Write last name ");
            String name = new Scanner(System.in).nextLine();
            if (GenericChecks.checforName(name)) {
                patcient.setLastName(name);
                break;
            }
        }
        while (true) {
            System.out.println("Write gender");
            String name = new Scanner(System.in).nextLine();
            if (name.equalsIgnoreCase(Gender.FEMALE.name())) {
                patcient.setGender(Gender.FEMALE);
                break;
            } else if (name.equalsIgnoreCase(Gender.MALE.name())) {
                patcient.setGender(Gender.MALE);
                break;
            }
        }
        while (true) {
            try {
                System.out.println("Write age ");
                int name = new Scanner(System.in).nextInt();
                if (name > 0) {
                    patcient.setAge(name);
                    break;
                }
            } catch (InputMismatchException exception) {
                System.out.println("Write only numbers");
            }
        }
        patcientDao.hospitalDao.getAllHospitals().stream().filter(hospital -> hospital.getId().equals(hospitalId)).findFirst().orElseThrow(() -> new NotFound("Not found")).getPatients().add(patcient);
        return "Success";
    }

    @Override
    public void removeById(Long id) {
        patcientDao.hospitalDao.getAllHospitals().forEach(hospital -> {
            List<Patcient> patients = hospital.getPatients();
            patients.removeIf(patient -> patient.getId().equals(id));
            hospital.setPatients(patients);
        });
    }

    @Override
    public String updateById(Long id, Patcient patcient) {
        List<Hospital> hospitals = patcientDao.hospitalDao.getAllHospitals();
        int indexHospital = IntStream.range(0, hospitals.size())
                .filter(i -> hospitals.get(i).getPatients().stream()
                        .anyMatch(patcient1 -> patcient1.getId().equals(id)))
                .findFirst()
                .orElse(-1);
        if (indexHospital != -1) {
            int indexPatcient = IntStream.range(0, hospitals.get(indexHospital).getPatients().size()).filter(
                    i -> hospitals.get(i).getPatients().stream().anyMatch(patcient1 -> patcient1.getId().equals(id))).findFirst().orElse(-1);
            System.out.println("""
                    Choose command for update
                    1.Name
                    2.Last name
                    3.Gender
                    4.Age
                                       
                    """);
            try {
                int num = new Scanner(System.in).nextInt();
                switch (num) {
                    case 1 -> {
                        while (true) {
                            System.out.println("Write name");
                            String name = new Scanner(System.in).nextLine();
                            if (GenericChecks.checforName(name)) {
                                patcientDao.hospitalDao.getAllHospitals().get(indexHospital).getPatients().get(indexPatcient).setFirstName(name);
                                return "Success";
                            }
                        }
                    }
                    case 2 -> {
                        while (true) {
                            System.out.println("Write last");
                            String name = new Scanner(System.in).nextLine();
                            if (GenericChecks.checforName(name)) {
                                patcientDao.hospitalDao.getAllHospitals().get(indexHospital).getPatients().get(indexPatcient).setLastName(name);
                                return "Success";
                            }
                        }
                    }
                    case 3 -> {
                        while (true) {
                            System.out.println("Write gender");
                            String name = new Scanner(System.in).nextLine();
                            if (name.equalsIgnoreCase(Gender.FEMALE.name())) {
                                patcientDao.hospitalDao.getAllHospitals().get(indexHospital).getPatients().get(indexPatcient).setGender(Gender.FEMALE);
                                return "Success";
                            } else if (name.equalsIgnoreCase(Gender.MALE.name())) {
                                patcientDao.hospitalDao.getAllHospitals().get(indexHospital).getPatients().get(indexPatcient).setGender(Gender.MALE);
                                return "Success";
                            }
                        }
                    }
                    case 4 -> {
                        while (true) {
                            System.out.println("Write age");
                            try {
                                int name = new Scanner(System.in).nextInt();
                                if (name > 0) {
                                    patcientDao.hospitalDao.getAllHospitals().get(indexHospital).getPatients().get(indexPatcient).setAge(name);
                                    return "Success";
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Write number");
                            }

                        }
                    }
                }
            } catch (InputMismatchException exception) {
                System.out.println("Write  number");
            }
        }
        throw new NotFound("Not found");
    }
    public Boolean addPatcient(int index,LinkedList<Patcient> patcients) {
        List<Patcient> patcients1 = patcientDao.hospitalDao.getAllHospitals().get(index).getPatients();
        patcients1.addAll(patcients);
        patcientDao.hospitalDao.getAllHospitals().get(index).setPatients(patcients1);
        return true;
    }
    public String myadd(Long id, LinkedList<Patcient> patcients) {
        boolean isyeas = true;
        int index = 0;
        for (int i = 0; i < patcientDao.hospitalDao.getAllHospitals().size(); i++) {
            if (patcientDao.hospitalDao.getAllHospitals().get(i).getId().equals(id)) {
                index = i;
                isyeas = false;
            }
        }
        if (!isyeas) {
            addPatcient(index, patcients);
            return "Success";
        }
        throw new NotFound("Not found");
    }


    @Override
    public String addPatientsToHospital(Long id, List<Patcient> patients) {
        patcientDao.hospitalDao.getAllHospitals().stream().filter(hospital -> hospital.getId().equals(id)).findFirst().orElseThrow(() -> new NotFound("Not found"));
        List<Patcient> patcients = new ArrayList<>();
        for (Patcient patsient :
                patients) {
            while (true) {
                System.out.println("Write name");
                String name = new Scanner(System.in).nextLine();
                if (GenericChecks.checforName(name)) {
                    patsient.setFirstName(name);
                    patsient.setId(++PatcientImpl.Id);
                    break;
                }
            }
            while (true) {
                System.out.println("Write last name");
                String name = new Scanner(System.in).nextLine();
                if (GenericChecks.checforName(name)) {
                    patsient.setLastName(name);
                    break;
                }
            }
            while (true) {
                System.out.println("Write age ");
                int name = new Scanner(System.in).nextInt();
                if (name > 0) {
                    patsient.setAge(name);
                    break;
                }
            }
            while (true) {
                System.out.println("Write gender");
                String name = new Scanner(System.in).nextLine();
                if (name.equalsIgnoreCase(Gender.MALE.name())) {
                    patsient.setGender(Gender.MALE);
                    break;
                } else if (name.equalsIgnoreCase(Gender.FEMALE.name())) {
                    patsient.setGender(Gender.FEMALE);
                    break;
                }
            }
            patcients.add(patsient);
        }
        patcientDao.hospitalDao.getAllHospitals().stream().filter(hospital -> hospital.getId().equals(id)).findFirst().get().getPatients().addAll(patcients);
        return "Success";
    }

    @Override
    public Patcient getPatientById(Long id) {
        return patcientDao.hospitalDao.getAllHospitals().stream().flatMap(hospital -> hospital.getPatients().stream().filter(patcient -> patcient.getId().equals(id))).findFirst().orElseThrow(() -> new NotFound("Not found"));
    }

    @Override
    public Map<Integer, List<Patcient>> getPatientByAge() {
        List<Patcient> patients = patcientDao.hospitalDao.getAllHospitals().stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .collect(Collectors.toList());

        return patients.stream()
                .collect(Collectors.groupingBy(Patcient::getAge));
    }


    @Override
    public List<Patcient> sortPatientsByAge(String ascOrDesc) {
        List<Patcient> patcients3 = new ArrayList<>();
        for (int i = 0; i < patcientDao.hospitalDao.getAllHospitals().size(); i++) {
            patcients3.addAll(patcientDao.hospitalDao.getAllHospitals().get(i).getPatients());
        }

        if (ascOrDesc.equalsIgnoreCase("asc")) {
            patcients3.sort(PatcientImpl.sortbyage);
            return patcients3;
        } else if (ascOrDesc.equalsIgnoreCase("Desc")) {
            patcients3.sort(PatcientImpl.sortbyage.reversed());
            return patcients3;
        }
        throw new NotFound("Write aasc or desc");
    }

    public static Comparator<Patcient> sortbyage = new Comparator<Patcient>() {
        @Override
        public int compare(Patcient o1, Patcient o2) {
            return o1.getAge() - o2.getAge();
        }
    };
}
