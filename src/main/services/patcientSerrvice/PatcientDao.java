package main.services.patcientSerrvice;

import main.models.Patcient;
import main.services.hospitalService.HospitalDao;

import java.util.List;

public class PatcientDao {
    final HospitalDao hospitalDao;

    public PatcientDao(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }
    public boolean addPatcientsToHospital(List<Patcient> patcientssInner,int index){
        List<Patcient> patients = hospitalDao.getAllHospitals().get(index).getPatients();
        patients.addAll(patcientssInner);
        hospitalDao.getAllHospitals().get(index).setPatients(patients);
        return true;
    }
    public List<Patcient> getPatcientsInHospital(int index){
        return hospitalDao.getAllHospitals().get(index).getPatients();
    }
    public boolean removePatcientinHospital(int indexhospital,int indexPatcient){
        hospitalDao.getAllHospitals().get(indexhospital).getPatients().remove(indexPatcient);
        return true;
    }
}
