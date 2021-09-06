package school;

import java.util.Date;

public class OVChipkaart {
    Double saldo;
    Date geldigTot;
    Integer klasse;
    Integer Kaartnummer;
    Reiziger reiziger;

    public OVChipkaart(Reiziger reiziger, Double saldo, Date geldigTot, Integer klasse, Integer kaartnummer) {
        this.reiziger = reiziger;
        this.saldo = saldo;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        Kaartnummer = kaartnummer;
    }

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

    public String toString(){
        return String.format("Kaart van %s, geldig tot %s, saldo %s, klasse %s, kaartnummer %s", this.getReiziger().getAchternaam(), this.getGeldigTot(), this.getSaldo(), this.getKlasse(), this.getKaartnummer());
    }
}
