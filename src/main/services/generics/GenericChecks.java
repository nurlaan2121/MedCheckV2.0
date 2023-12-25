package main.services.generics;

import main.models.Department;
import main.models.Hospital;

import java.util.List;
import java.util.function.Predicate;

public class GenericChecks<T> {
    public static Boolean chechUnicalName(List<? extends Hospital> t, String name){
        for (int i = 0; i < t.size(); i++) {
            for (int i1 = 0; i1 < t.get(i).getDepartments().size(); i1++) {
                if (name.equalsIgnoreCase(t.get(i).getDepartments().get(i1).getDepartmentName())){
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean checforName(String string){
        return string.length() > 2;
    }
}
