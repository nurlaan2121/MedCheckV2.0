package main;

import main.models.*;
import main.services.departmentService.DepartmentDao;
import main.services.departmentService.DepartmentImpl;
import main.services.departmentService.DepartmentInterface;
import main.services.doctorService.DoctorDao;
import main.services.doctorService.DoctorImpl;
import main.services.doctorService.DoctorInterface;
import main.services.generics.GenericService;
import main.services.hospitalService.HospitalDao;
import main.services.hospitalService.HospitalImpl;
import main.services.hospitalService.HospitalService;
import main.services.hospitalService.database.DataBase;
import main.services.patcientSerrvice.PatcientDao;
import main.services.patcientSerrvice.PatcientImpl;
import main.services.patcientSerrvice.PatcientService;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        HospitalDao hospitalDao = new HospitalDao(dataBase);
        HospitalService hospitalService = new HospitalImpl(hospitalDao);
        DoctorDao doktorDao = new DoctorDao(hospitalDao);
        DoctorInterface doctorService = new DoctorImpl(doktorDao);
        PatcientDao patcientDao = new PatcientDao(hospitalDao);
        PatcientService patcientService = new PatcientImpl(patcientDao);
        DepartmentDao departmentDao = new DepartmentDao(hospitalDao);
        DepartmentInterface departmentService = new DepartmentImpl(departmentDao);
        GenericService<Patcient> genericServiccPatcient = new PatcientImpl(patcientDao);
        GenericService<Doctor> genericServiceDoctor = new DoctorImpl(doktorDao);
        GenericService<Department> genericServiceDepartament = new DepartmentImpl(departmentDao);
        LOOPONE:
        while (true) {
            System.out.println("""
                    Choose command
                    1.Add Hospital;
                    2.Find HospitalById;
                    3.Get All Hospital;
                    4.Get All Patient From Hospital;
                    5.Delete Hospital By Id;
                    6.Get All Hospital By Address;
                    7.Get All Department By  Hospital;
                    8.Find Department By Name
                    9.Add Department to hospital
                    10.Remove Deportment By Id
                    11.Update department By Id
                    12.Find Doctor By Id
                    13.Assign Doctor To Department
                    14.Get AllDoctors By Hospital Id;
                    15.Get AllDoctors By Department Id;
                    16.Add Doctor to hospital
                    17.Remove Doctor By Id
                    18.Update doctor By Id
                    19.Add Patients To Hospital
                    20.Get Patient By Id;
                    21.Get Patient By Age;
                    22.Sort Patients By Age
                    23.Add patsient to hospital
                    24.Remove patsient By Id
                    25.Update patsient By Id
                    0.Exit
                    """);
            try {
                int actionMain = new Scanner(System.in).nextInt();
                switch (actionMain) {
                    case 1 -> {
                        System.out.println(hospitalService.addHospital(new Hospital()));
                    }
                    case 2 -> {
                        System.out.println("Write id: ");
                        try {
                            long id = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(hospitalService.findHospitalById(id));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("San jaz tuugan");
                        }
                    }
                    case 3 -> {
                        System.out.println(hospitalService.getAllHospital());
                    }
                    case 4 -> {
                        System.out.println("Write id hospital: ");
                        try {
                            long id = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(hospitalService.getAllPatientFromHospital(id));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("San jaz");
                        }
                    }
                    case 5 -> {
                        try {
                            System.out.println("Write id hospital: ");
                            long id = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(hospitalService.deleteHospitalById(id));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("Write number");
                        }
                    }
                    case 6 -> {
                        System.out.println("Write adres: ");
                        String adress = new Scanner(System.in).nextLine();
                        try {
                            System.out.println(hospitalService.getAllHospitalByAddress(adress));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 7 -> {
                        try {
                            System.out.println("Write id hospital: ");
                            long id = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(departmentService.getAllDepartmentByHospital(id));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("Write id");
                        }
                    }
                    case 8 -> {
                        System.out.println("Write name:");
                        String name = new Scanner(System.in).nextLine();
                        try {
                            System.out.println(departmentService.findDepartmentByName(name));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 9 -> {
                        try {
                            System.out.println("Write hospital id: ");
                            long id = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(genericServiceDepartament.add(id, new Department()));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("Write id");
                        }
                    }
                    case 10 -> {
                        try {
                            System.out.println("Write hospital id: ");
                            long id = new Scanner(System.in).nextLong();
                            try {
                                genericServiceDepartament.removeById(id);
                            } catch (RuntimeException e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("Write number!");
                        }
                    }
                    case 11 -> {
                        try {
                            System.out.println("Write id ");
                            long id = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(genericServiceDepartament.updateById(id, new Department()));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("San jaz");
                        }
                    }
                    case 12 -> {
                        try {
                            System.out.println("Write id");
                            long id = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(doctorService.findDoctorById(id));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("Write id");

                        }
                    }
                    case 13 -> {
                        System.out.println("Write departmend id: ");
                        try {
                            long id = new Scanner(System.in).nextLong();
                            if (id > 0) {
                                System.out.println("Write id with space");
                                String ids = new Scanner(System.in).nextLine();
                                LinkedList<Long> moreids = new LinkedList<>();
                                String ids2 = ids.trim();
                                String[] s = ids2.split(" ");
                                String name = "1,2,3,5,6,7,8,9,0";
                                int amount = 0;
//
                                for (String ee : s) {
                                    if (name.contains(ee)) {
                                        amount++;
                                    }
                                }
                                if (amount == s.length) {
                                    for (String dd : s) {
                                        moreids.add(Long.valueOf(dd));
                                    }
                                    try {
                                        System.out.println(doctorService.assignDoctorToDepartment(id, moreids));
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                } else System.out.println("Write only numbers please!");
                            } else {
                                System.out.println("Write id!");
                            }
                        } catch (InputMismatchException exception) {
                            System.out.println("Write number");
                        }
                    }
                    case 14 -> {
                        try {
                            prints("Hospital");
                            long id = new Scanner(System.in).nextLong();
                            if (checkforId(id)) {
                                try {
                                    System.out.println(doctorService.getAllDoctorsByHospitalId(id));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }

                    }
                    case 15 -> {
                        try {
                            prints("Hospital");
                            long id = new Scanner(System.in).nextLong();
                            if (checkforId(id)) {
                                try {
                                    System.out.println(doctorService.getAllDoctorsByDepartmentId(id));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 16 -> {
                        try {
                            prints("Hospital");
                            long id = new Scanner(System.in).nextLong();
                            if (checkforId(id)) {
                                try {
                                    System.out.println(genericServiceDoctor.add(id, new Doctor()));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 17 -> {
                        try {
                            prints("Doctor");
                            long id = new Scanner(System.in).nextLong();
                            if (checkforId(id)) {
                                try {
                                    genericServiceDoctor.removeById(id);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 18 -> {
                        try {
                            prints("Doctor");
                            long id = new Scanner(System.in).nextLong();
                            if (checkforId(id)) {
                                try {
                                    genericServiceDoctor.updateById(id, new Doctor());
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 19 -> {
                        System.out.println("Choose add format (1) for fori (2) for universal");
                        String action = new Scanner(System.in).nextLine();
                        switch (action) {
                            case "1" -> {
                                prints("Hospital");
                                boolean isyeas = false;
                                Long id = new Scanner(System.in).nextLong();
                                for (int i = 0; i < hospitalDao.getAllHospitals().size(); i++) {
                                    if (id.equals(hospitalDao.getAllHospitals().get(i).getId())) {
                                        isyeas = true;
                                    }
                                }
                                if (isyeas) {
                                    System.out.println("Write how amount add patcient: ");
                                    LinkedList<Patcient> patcients = new LinkedList<>();
                                    try {
                                        int num = new Scanner(System.in).nextInt();
                                        for (int i = 0; i < num; i++) {
                                            patcients.add(new Patcient());
                                        }
                                        System.out.println(patcientService.addPatientsToHospital(id, patcients));
                                    } catch (InputMismatchException exception) {
                                        System.out.println("Please write only numbers");
                                    }
                                } else System.out.println("Write correct id please!");


                            }
                            case "2" -> {
                                try {
                                    prints("Hospital");
                                    boolean isyeas = false;
                                    Long id = new Scanner(System.in).nextLong();
                                    for (int i = 0; i < hospitalDao.getAllHospitals().size(); i++) {
                                        if (id.equals(hospitalDao.getAllHospitals().get(i).getId())) {
                                            isyeas = true;
                                        }
                                    }
                                    if (isyeas) {
                                        System.out.println("Write 1 - (name) and one space 2-(LastName) and one space 3-(Gender) and one space 4-(Age)  5-(,)  ||  exaple(Nurlan Ikramov Male 23,)");
                                        String many = new Scanner(System.in).nextLine();
                                        String[] split = many.trim().split(",");
                                        String[] split2 = many.trim().split(",");
                                        boolean theEndBoolean = true;
                                        for (int r = 0; r < split2.length; r++) {
                                            String[] s = split2[r].split(" ");
                                            String[] words = {"A", "E", "I", "O", "U", "Y", "B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z", "a", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "o", "п", "р", "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"};
                                            for (int i = 0; i < words.length; i++) {
                                                if (s[3].toLowerCase().contains(words[i].toLowerCase())) {
                                                    theEndBoolean = false;
                                                }
                                            }
                                        }
                                        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
                                        LinkedList<Patcient> patcients = new LinkedList<>();
                                        for (String soz : split) {
                                            String[] split1 = soz.trim().split(" ");
                                            boolean contince = false;
                                            for (int i = 0; i < numbers.length; i++) {
                                                if (!split1[3].toLowerCase().contains(numbers[i].toLowerCase())) {
                                                    contince = true;
                                                }
                                            }
                                            if (theEndBoolean && contince && split1[2].equalsIgnoreCase("male") || split1[2].equalsIgnoreCase("female")) {
                                                Patcient patcient = new Patcient();
                                                patcient.setFirstName(split1[0]);
                                                patcient.setLastName(split1[1]);
                                                patcient.setId(++PatcientImpl.Id);
                                                if (split1[2].equalsIgnoreCase("Female")) {
                                                    patcient.setGender(Gender.FEMALE);
                                                } else if (split1[2].equalsIgnoreCase("Male")) {
                                                    patcient.setGender(Gender.MALE);
                                                }
                                                patcient.setAge(Integer.parseInt(split1[3]));
                                                patcients.add(patcient);

                                            } else System.out.println("Write correct info");
                                        }
                                        System.out.println(patcientService.myadd(id, patcients));
                                        System.out.println(patcients);
                                    } else System.out.println("Write correct id!");
                                } catch (InputMismatchException exception) {
                                    printforException();
                                }
                            }
                        }

                    }
                    case 20 -> {
                        try {
                            prints("Patcient");
                            long id = new Scanner(System.in).nextLong();
                            if (checkforId(id)) {
                                try {
                                    System.out.println(patcientService.getPatientById(id));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 21 -> {
                        System.out.println(patcientService.getPatientByAge());
                    }
                    case 22 -> {
                        try {
                            System.out.println("Write Asc or Desc");
                            String ascorDesc = new Scanner(System.in).nextLine();
                            try {
                                System.out.println(patcientService.sortPatientsByAge(ascorDesc));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 23 -> {
                        try {
                            prints("Hospital");
                            long id = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(genericServiccPatcient.add(id, new Patcient()));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 24 -> {
                        try {
                            prints("Patcient");
                            long ascorDesc = new Scanner(System.in).nextLong();
                            try {
                                genericServiccPatcient.removeById(ascorDesc);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 25 -> {
                        try {
                            prints("Patcient");
                            long ascorDesc = new Scanner(System.in).nextLong();
                            try {
                                System.out.println(genericServiccPatcient.updateById(ascorDesc, new Patcient()));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (InputMismatchException exception) {
                            printforException();
                        }
                    }
                    case 0 -> {
                        break LOOPONE;
                    }
                }
            } catch (InputMismatchException exception) {
                System.out.println("Please write only numbers");
            }

        }

    }

    public static void prints(String word) {
        System.out.println(" Write " + word + " id!");
    }

    public static Boolean checkforId(Long id) {
        if (id > 0) {
            return true;
        }
        return false;
    }

    public static void printforException() {
        System.out.println("Please write only numbers!");
    }
}