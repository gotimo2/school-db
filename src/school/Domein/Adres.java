package school.Domein;

import javax.persistence.*;

@Entity
public class Adres {

    @Id
    @Column(name = "adres_id")
    @GeneratedValue
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;


    @OneToOne
    @JoinColumn(name = "reiziger_id", referencedColumnName = "reiziger_id")
    private Reiziger reiziger;

    public Adres() {

    }

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger r){
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = r;
    }

    //getter/setter
    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }


    //tostring
    public String toString(){
        return String.format("%s : %s %s, %s, %s ", this.id, this.huisnummer, this.straat, this.woonplaats, this.postcode);
    }
}
