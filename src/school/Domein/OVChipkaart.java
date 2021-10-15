package school.Domein;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {

    Double saldo;

    @Column(name = "geldig_tot")
    Date geldigTot;
    Integer klasse;

    @Id
    @Column(name="kaart_nummer")
    @GeneratedValue
    Integer Kaartnummer;

    @ManyToOne
    @JoinColumn(name="reiziger_id")
    Reiziger reiziger;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(name = "ov_chipkaart_product", joinColumns = {@JoinColumn(name = "kaart_nummer")}, inverseJoinColumns = {@JoinColumn(name = "product_nummer")})
    List<Product> producten = new ArrayList<Product>();

    public OVChipkaart(Reiziger reiziger, Double saldo, Date geldigTot, Integer klasse){
        this.reiziger = reiziger;
        this.saldo = saldo;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
    }
    public OVChipkaart() {

    }

    public List<Product> getProducten() {
        return producten;
    }

    public void setSaldo(double saldo){
        this.saldo = saldo;
    }


    public void addProduct(Product p){producten.add(p); p.addKaart(this);}


    public Integer getKaartnummer() {
        return Kaartnummer;
    }

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
        return String.format("Kaart van %s, geldig tot %s, saldo %s, klasse %s, kaartnummer %s", this.reiziger.getAchternaam(), this.geldigTot, this.saldo, this.klasse, this.Kaartnummer);
    }
}
