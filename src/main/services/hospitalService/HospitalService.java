package main.services.hospitalService;

import main.models.Hospital;
import main.models.Patcient;
import main.services.generics.GenericService;

import java.util.List;
import java.util.Map;

public interface HospitalService{
    String addHospital(Hospital hospital);
    Hospital findHospitalById(Long id);
    List<Hospital> getAllHospital();
    List<Patcient> getAllPatientFromHospital(Long id);
    String deleteHospitalById(Long id);
    Map<String, Hospital> getAllHospitalByAddress(String address);
}
