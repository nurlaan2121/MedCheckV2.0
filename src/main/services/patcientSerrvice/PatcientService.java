package main.services.patcientSerrvice;

import main.models.Patcient;
import main.services.generics.GenericService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface PatcientService extends GenericService<Patcient> {
    public String myadd(Long id, LinkedList<Patcient> patcients);
    String addPatientsToHospital(Long id, List<Patcient> patients);
    Patcient getPatientById(Long id);
    Map<Integer, List<Patcient>> getPatientByAge();
    List<Patcient> sortPatientsByAge(String ascOrDesc);
}
