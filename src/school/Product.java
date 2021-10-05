package school;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    private Integer product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(mappedBy = "producten")
    private List<OVChipkaart> kaarten = new ArrayList<OVChipkaart>();

    public void addKaart(OVChipkaart k) {kaarten.add(k);}

    public List<OVChipkaart> getKaarten() {
        return kaarten;
    }

    public Product(){}

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
