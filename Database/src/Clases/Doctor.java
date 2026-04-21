package Clases;
import java.time.LocalDate;

import Enums.Sex;

public class Doctor {
	
	private int medical_license_number;
	private String name;
	private String surname;
	private Sex sex;
	private LocalDate date_of_birth;
	private int phone_number;
	private String email;
    private String speciality;
    private byte[] photo;
    private double salary;
	private int amount_of_surgeries;
	
	
    //constructor
    public Doctor(int medical_license_number, String name, String surname,
            Sex sex,LocalDate date_of_birth, int phone_number, String email,
            String speciality, byte[] photo, double salary, int amount_of_surgeries) {

  this.medical_license_number = medical_license_number;
  this.name = name;
  this.surname = surname;
  this.sex = sex;
  this.date_of_birth = date_of_birth;
  this.phone_number = phone_number;
  this.email = email;
  this.speciality = speciality;
  this.photo = photo;
  this.salary = salary;
  this.amount_of_surgeries = amount_of_surgeries;
}

 //getters y setters
    public int getMedical_license_number() {
        return medical_license_number;
    }

    public void setMedical_license_number(int medical_license_number) {
        this.medical_license_number = medical_license_number;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    
    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
    
    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
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
    
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public int getAmount_of_surgeries() {
        return amount_of_surgeries;
    }

    public void setAmount_of_surgeries(int amount_of_surgeries) {
        this.amount_of_surgeries = amount_of_surgeries;
    }
    
}
