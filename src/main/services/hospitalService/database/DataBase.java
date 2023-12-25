package main.services.hospitalService.database;

import main.models.Hospital;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataBase {
    List<Hospital> hospitals = new ArrayList<>();

    public List<Hospital> getHospitals (){
        return hospitals;
    }
    public Boolean addHospital(Hospital hospital){
        return hospitals.add(hospital);
    }
    public void updateAfterRemove(List<Hospital> hospitalss){
        hospitals = hospitalss;
    }
}
