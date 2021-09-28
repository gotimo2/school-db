package school;

import java.util.ArrayList;

public class Product {
    private Integer product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private ArrayList<OVChipkaart> kaarten = new ArrayList<OVChipkaart>();
    public void addKaart(OVChipkaart k) {kaarten.add(k);}

    public ArrayList<OVChipkaart> getKaarten() {
        return kaarten;
    }

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Integer getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void verwijderKaart(OVChipkaart k){
        kaarten.removeIf(ka -> k.getKaartnummer().equals(ka.getKaartnummer()));
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", kaarten=" + kaarten +
                '}';
    }
}
