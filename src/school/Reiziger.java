package school;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Reiziger {

    //variabelen
    @Id
    @Column(name = "reiziger_id")
    int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;


    @OneToOne(mappedBy = "reiziger")
    private Adres adres;

    @OneToMany(mappedBy = "reiziger")
    private List<OVChipkaart> kaarten = new ArrayList<OVChipkaart>();

    //constructor
    public Reiziger( int id ,String vl, String tv, String an, Date gb){
        this.voorletters = vl;
        this.tussenvoegsel = tv;
        this.achternaam = an;
        this.geboortedatum = gb;
        this.id = id;
    }

    public Reiziger(){

    }

    //getters/setters

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }



    public String toString(){
        if(this.tussenvoegsel == null)
        {
            return String.format("#%s: %s. %s : %s {%s}", this.id , this.voorletters, this.achternaam, this.geboortedatum, this.adres);
        }
        else
            {
            return String.format("#%s: %s. %s %s : %s {%s} ", this.id , this.voorletters, this.tussenvoegsel, this.achternaam, this.geboortedatum, this.adres );
        }
    }


}
