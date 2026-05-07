package POJOS;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import Enums.Sex;

public class Patient {
	
	private int id;
    private Sex sex;
    private String name;
    private String surname;
    private LocalDate date_of_birth;
    private int height;
    private float weight;
    private byte[] photo;
    private String info;
    private String email;

    // Constructor_vacío_por_defecto
    public Patient() 
    {
    }

    public Patient(int id, Sex sex, String name, String surname, LocalDate date_of_birth, int height, float weight, byte[] photo, String info, String email) 
    {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.surname = surname;
        this.date_of_birth = date_of_birth;
        this.height = height;
        this.weight = weight;
        this.photo = photo;
        this.info = info;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", height=" + height +
                ", weight=" + weight +
                ", photo=" + Arrays.toString(photo) +
                ", info='" + info + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Patient patient = (Patient) object;
        return id == patient.id && height == patient.height && Float.compare(weight, patient.weight) == 0 && sex == patient.sex && Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(date_of_birth, patient.date_of_birth) && Arrays.equals(photo, patient.photo) && Objects.equals(info, patient.info) && Objects.equals(email, patient.email);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, sex, name, surname, date_of_birth, height, weight, info, email);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
	

}
