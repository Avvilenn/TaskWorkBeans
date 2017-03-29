package edu.javacourse.test;

import java.io.Serializable;

/**
 * Created by просто on 24.03.2017.
 */
public class Doctor implements Serializable {
    private String speciality;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "speciality='" + speciality + '\'' +
                '}';
    }
}
