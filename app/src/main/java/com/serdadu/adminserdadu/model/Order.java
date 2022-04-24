package com.serdadu.adminserdadu.model;

public class Order {
    Integer id;
    Double totalharga;
    String namapemesan,emailpemesan,hppemesan,totalhari,namapulau,tanggal,status,usernamepemesan,
    tanggalbooking;

    public Integer getId() {
        return id;
    }

    public Double getTotalharga() {
        return totalharga;
    }

    public String getNamapemesan() {
        return namapemesan;
    }

    public String getEmailpemesan() {
        return emailpemesan;
    }

    public String getHppemesan() {
        return hppemesan;
    }

    public String getTotalhari() {
        return totalhari;
    }

    public String getNamapulau() {
        return namapulau;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getStatus() { return status;}

    public String getUsernamepemesan() { return usernamepemesan;}

    public String getTanggalbooking() { return tanggalbooking;}
}
