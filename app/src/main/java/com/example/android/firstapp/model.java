package com.example.android.firstapp;

public class model
{
  String name,departement,email;
    model()
    {

    }
    public model(String name, String departement, String email) {
        this.name = name;
        this.departement = departement;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
