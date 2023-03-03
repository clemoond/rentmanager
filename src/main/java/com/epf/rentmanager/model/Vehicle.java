package com.epf.rentmanager.model;

public class Vehicle {
    private int id;
    private String constructeur;
    private String modele;
    private byte nb_places;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public byte getNb_places() {
        return nb_places;
    }

    public void setNb_places(byte nb_places) {
        this.nb_places = nb_places;
    }

    public Vehicle(int id, String constructeur, byte nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        //this.modele = modele;
        this.nb_places = nb_places;
    }

    public Vehicle(){
        this.id = 0;
        this.constructeur = "";
        //this.modele = "";
        this.nb_places = 0;
    }

    public Vehicle(Vehicle vehicle){
        this.id = vehicle.getId();
        this.constructeur = vehicle.getConstructeur();
        //this.modele = vehicle.getModele();
        this.nb_places = vehicle.getNb_places();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }
}
