package school;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","password");
        props.setProperty("ssl","false");
        Connection conn = DriverManager.getConnection(url, props);

        ReizigerDAOPsql rdaopsql = new ReizigerDAOPsql(conn);
        AdresDAOPsql adaopsql = new AdresDAOPsql(conn);
        OVChipkaartDAO odaopsql = new OVChipkaartDAOPsql(conn);

        rdaopsql.setAdresDAO(adaopsql);
        adaopsql.setReizigerdao(rdaopsql);

        testReizigerDAO(rdaopsql);
        testAdresDAO(adaopsql, rdaopsql);
        testOVChipkaartDAO(odaopsql, rdaopsql);
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);

        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("test update\n");
        sietske.setAchternaam("Boersen");
        rdao.update(sietske);
        System.out.println("test delete \n");
        rdao.delete(sietske);
        System.out.println("Test getbyid");
        System.out.println(rdao.findByID(1));
        System.out.println("Test findByGeboortedatum");
        System.out.println(rdao.findByGbDatum("2002-09-17"));



    }

    public static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");
        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(sietske);
        Adres bijlstraat = new Adres(77, "1402RD", "119A", "Bijlstraat", "Bussum", 77);

        System.out.println("Test adres save");
        adao.save(bijlstraat);
        System.out.println("Test findAll");
        List<Adres> aL = adao.findAll();
        for (Adres i: aL) { System.out.println(i);}
        System.out.println("Test findByReiziger");
        System.out.println(adao.findByReiziger(sietske));
        System.out.println("Test update");
        bijlstraat.setHuisnummer("8");
        System.out.println(adao.update(bijlstraat));
        System.out.println("Test delete");
        System.out.println(adao.delete(bijlstraat));
        System.out.println(rdao.delete(sietske));
    }
    public static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n--------------TEST OVChipkaartDAO-------------");
        OVChipkaart kaart = new OVChipkaart(rdao.findByID(3), 300.0, java.sql.Date.valueOf("2023-04-04"), 1, 10382);

        System.out.println("Test save kaart");
        System.out.println(odao.save(kaart));
        System.out.println("Test update kaart");
        kaart.setSaldo(100.1);
        System.out.println(odao.update(kaart));
        System.out.println("Test findbyreiziger");
        for (OVChipkaart o : odao.findByReiziger(rdao.findByID(3))){
            System.out.println(o.toString());
        }
        System.out.println("Test delete kaart");
        System.out.println(odao.delete(kaart));

    }

}
