package main.services.hospitalService;

import main.models.Hospital;
import main.models.Patcient;
import main.services.generics.GenericChecks;
import main.services.myExceptions.NotFound;

import java.util.*;

public class HospitalImpl implements HospitalService {
    final HospitalDao hospitalDao;
    Long id = 0L;

    public HospitalImpl(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }
//    public String addd(Hospital hospital) {
//        List<Hospital> hospitals = hospitalDao.getAllHospitals();
//        hospitals.stream()
//                .filter(h -> h.getId().equals(hospitalId))
//                .findFirst()
//                .ifPresent(h -> {
//                    System.out.println("Write name:");
//                    String name = new Scanner(System.in).nextLine();
//                    if (GenericChecks.checforName(name)) {
//                        hospital.setHospitalName(name);
//                        System.out.println("Write address:");
//                        String address = new Scanner(System.in).nextLine();
//                        if (GenericChecks.checforName(address)) {
//                            hospital.setAddress(address);
//                            hospitalDao.addHospital(hospital);
//                        }
//                    }
//                });
//
//        return "Success";
//    }
    @Override
    public String addHospital(Hospital hospital) {
        while (true) {
            System.out.println("Write name :");
            String name = new Scanner(System.in).nextLine();
            if (GenericChecks.checforName(name)) {
                hospital.setHospitalName(name);
                hospital.setId(++id);
                break;
            }
        }
        while (true) {
            System.out.println("Write adress :");
            String name = new Scanner(System.in).nextLine();
            if (GenericChecks.checforName(name)) {
                hospital.setAddress(name);
                break;
            }
        }
        hospitalDao.addHospital(hospital);
        return "Success";
    }

    @Override
    public Hospital findHospitalById(Long id) {
        Hospital hospital = hospitalDao.getAllHospitals().
                stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(null);
        if (hospital != null){
            return hospital;
        }else throw new NotFound("Not found");
    }

    @Override
    public List<Hospital> getAllHospital() {
        return hospitalDao.getAllHospitals();
    }

    @Override
    public List<Patcient> getAllPatientFromHospital(Long id) {
        Hospital hospital = hospitalDao.getAllHospitals().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(null);
        if (hospital !=null){
            return hospital.getPatients();
        }throw new NotFound("Not found");
    }

    @Override
    public String deleteHospitalById(Long id) {
        List<Hospital> list = hospitalDao.getAllHospitals().stream().filter(e -> !e.getId().equals(id)).toList();
        if (list.size() < hospitalDao.getAllHospitals().size()){
            hospitalDao.updateAfterDelete(list);
            return "Success";
        }
        throw new NotFound("Not found");

    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
      Hospital hospital = hospitalDao.getAllHospitals().stream().filter(e -> e.getAddress().equalsIgnoreCase(address)).findAny().orElse(null);
        if (hospital !=null){
            Map<String,Hospital> map = new HashMap<>();
            map.put(hospital.getAddress(),hospital);
            return map;
        }
        throw new NotFound("Not found");
    }
}
