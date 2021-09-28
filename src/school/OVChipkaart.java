package school;

import java.util.ArrayList;
import java.util.Date;

public class OVChipkaart {
    //variabelen
    Double saldo;
    Date geldigTot;
    Integer klasse;
    Integer Kaartnummer;
    Reiziger reiziger;
    ArrayList<Product> producten = new ArrayList<Product>();

    //constructor
    public OVChipkaart(Reiziger reiziger, Double saldo, Date geldigTot, Integer klasse, Integer kaartnummer) {
        this.reiziger = reiziger;
        this.saldo = saldo;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        Kaartnummer = kaartnummer;
    }

    //getters/setters
    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }


    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public Integer getKlasse() {
        return klasse;
    }

    public void setKlasse(Integer klasse) {
        this.klasse = klasse;
    }

    public Integer getKaartnummer() {
        return Kaartnummer;
    }

    public void setKaartnummer(Integer kaartnummer) {
        Kaartnummer = kaartnummer;
    }

    public ArrayList<Product> getProducten() {
        return producten;
    }

    public void addProduct(Product p){producten.add(p); p.addKaart(this);}

    public void verwijderProduct(Product p){
        ArrayList<Product> ObjstoRemove = new ArrayList<Product>();
        for (Product pr:producten
             ) {
            if(p.getProduct_nummer().equals(pr.getProduct_nummer())){
                ObjstoRemove.add(pr);
                pr.verwijderKaart(this);
            }

        }
        producten.removeAll(ObjstoRemove);
    }

    public String toString(){
        return String.format("Kaart van %s, geldig tot %s, saldo %s, klasse %s, kaartnummer %s", this.getReiziger().getAchternaam(), this.getGeldigTot(), this.getSaldo(), this.getKlasse(), this.getKaartnummer());
    }
}
