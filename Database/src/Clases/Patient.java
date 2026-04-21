package Clases;
import java.time.LocalDate;

public class Patient {
	  //atributos
    private String name;
    private String surname;
    private String dni;
    private LocalDate date_of_birth;
    private Sex sex;
    private int height;
    private int weight;
    private byte[] photo;
    private int phone_number;
    private String email;
    private String address;
    private String payment_method;
    private String clinical_history;
    private String personal_information;
    
    //constructor
    public Patient(String name, String surname, String dni, LocalDate date_of_birth,
            Sex sex, Integer height, Integer weight, byte[] photo,
            Integer phone_number, String email, String address,
            String payment_method, String clinical_history,
            String personal_information) {

 this.name = name;
 this.surname = surname;
 this.dni = dni;
 this.date_of_birth = date_of_birth;
 this.sex = sex;
 this.height = height;
 this.weight = weight;
 this.photo = photo;
 this.phone_number = phone_number;
 this.email = email;
 this.address = address;
 this.payment_method = payment_method;
 this.clinical_history = clinical_history;
 this.personal_information = personal_information;


}
    //getters y setters
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Integer getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Integer phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getClinical_history() {
        return clinical_history;
    }

    public void setClinical_story(String clinical_history) {
        this.clinical_history= clinical_history;
    }

    public String getPersonal_information() {
        return personal_information;
    }

    public void setPersonal_information(String personal_information) {
        this.personal_information = personal_information;
    }

}
