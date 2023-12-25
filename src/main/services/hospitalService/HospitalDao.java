package main.services.hospitalService;

import main.models.Hospital;
import main.services.hospitalService.database.DataBase;

import java.util.List;

public class HospitalDao {
    final DataBase dataBase;

    public HospitalDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }
    public Boolean addHospital(Hospital hospital){
        return dataBase.addHospital(hospital);
    }
    public List<Hospital> getAllHospitals(){
        return dataBase.getHospitals();
    }
    public void removeHospital(int index){
        dataBase.getHospitals().remove(index);
    }


    public void updateAfterDelete(List<Hospital> list) {
        dataBase.updateAfterRemove(list);
    }
}
