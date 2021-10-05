package school;

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
    Integer Kaartnummer;

    @ManyToOne
    @JoinColumn(name="reiziger_id")
    Reiziger reiziger;

    @ManyToMany
    @JoinTable(name = "ov_chipkaart_product", joinColumns = {@JoinColumn(name = "kaart_nummer")}, inverseJoinColumns = {@JoinColumn(name = "product_nummer")})
    List<Product> producten = new ArrayList<Product>();

    public OVChipkaart() {

    }


    public String toString(){
        return String.format("Kaart van %s, geldig tot %s, saldo %s, klasse %s, kaartnummer %s", this.reiziger.getAchternaam(), this.geldigTot, this.saldo, this.klasse, this.Kaartnummer);
    }
}
