package POJOS;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import Enums.Sex;

public class Doctor 
{
	private int id;
    private String name;
    private String surname;
    private byte[] photo;
    private Sex sex;
    private String email;
    private String speciality;
    private LocalDate date_of_birth;

    public Doctor() 
    {
    }
    public Doctor(int id, String name, String surname, byte[] photo, Sex sex, String email, String speciality, LocalDate date_of_birth)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.photo = photo;
        this.sex = sex;
        this.email = email;
        this.speciality = speciality;
        this.date_of_birth = date_of_birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", speciality='" + speciality + '\'' +
                ", date_of_birth=" + date_of_birth +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Doctor doctor = (Doctor) object;
        return id == doctor.id && Objects.equals(name, doctor.name) && Objects.equals(surname, doctor.surname) && Arrays.equals(photo, doctor.photo) && sex == doctor.sex && Objects.equals(email, doctor.email) && Objects.equals(speciality, doctor.speciality) && Objects.equals(date_of_birth, doctor.date_of_birth);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, surname, sex, email, speciality, date_of_birth);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

}
