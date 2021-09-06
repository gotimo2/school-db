package school;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws SQLException {
        //maak connectie
        String url = "jdbc:postgresql://localhost/ovchip";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","password");
        props.setProperty("ssl","false");
        Connection conn = DriverManager.getConnection(url, props);

        //maak DAO's
        ReizigerDAOPsql rdaopsql = new ReizigerDAOPsql(conn);
        AdresDAOPsql adaopsql = new AdresDAOPsql(conn);
        OVChipkaartDAO odaopsql = new OVChipkaartDAOPsql(conn);

        //configureer DAO's
        rdaopsql.setAdresDAO(adaopsql);
        rdaopsql.setOdao(odaopsql);
        adaopsql.setReizigerdao(rdaopsql);

        //test DAO's
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

        //test de DAO
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        System.out.println("[Test] Update, achternaam moet Boersen zijn");
        sietske.setAchternaam("Boersen");
        rdao.update(sietske);
        System.out.println(rdao.findByID(77));

        System.out.println("\n[Test] Delete, geeft true als het werkt of een bug als niet");
        System.out.println(rdao.delete(sietske));

        System.out.println("\n[Test] findById, moet g van rijn geven.");
        System.out.println(rdao.findByID(1));

        System.out.println("\n[Test] findByGeboortedatum, moet list met iedereen op 2002-09-17 geven");
        System.out.println(rdao.findByGbDatum("2002-09-17"));

    }

    public static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");
        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(sietske);

        //geef een adres aan de reiziger
        Adres bijlstraat = new Adres(77, "1402RD", "119A", "Bijlstraat", "Bussum", 77);


        //run tests
        System.out.println("[Test] adres save, geeft true of bug, volgende test zou bijlstraat moeten geven");
        System.out.println(adao.save(bijlstraat));

        System.out.println("\n[Test] findAll");
        List<Adres> aL = adao.findAll();
        for (Adres i: aL) { System.out.println(i);}

        System.out.println("\n[Test] findByReiziger, geeft Sietske Boers.");
        System.out.println(adao.findByReiziger(sietske));

        System.out.println("\n[Test] update, moet bijlstraat moet huisnummer 8 hebben");
        bijlstraat.setHuisnummer("8");
        adao.update(bijlstraat);
        System.out.println(adao.findByReiziger(sietske));

        System.out.println("\n[Test] delete, moet true geven");
        System.out.println(adao.delete(bijlstraat));
        rdao.delete(sietske);
    }
    public static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n--------------TEST OVChipkaartDAO-------------");

        //maak kaart
        OVChipkaart kaart = new OVChipkaart(rdao.findByID(3), 300.0, java.sql.Date.valueOf("2023-04-04"), 1, 10382);

        //test DAO
        System.out.println("\n[Test] save kaart, moet true geven");
        System.out.println(odao.save(kaart));

        System.out.println("\n[Test] update kaart, moet true geven en in de volgende test moet kaar 10382 100.1 euro hebben");
        kaart.setSaldo(100.1);
        System.out.println(odao.update(kaart));

        System.out.println("\n[Test] findbyreiziger, moet onder andere kaart 10382 laten zien");
        for (OVChipkaart o : odao.findByReiziger(rdao.findByID(3))){
            System.out.println(o.toString());
        }

        System.out.println("\n[Test] delete kaart, moet true geven");
        System.out.println(odao.delete(kaart));

    }

}
