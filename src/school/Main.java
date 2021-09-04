package school;

import java.sql.*;
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
        AdresDAOpsql adaopsql = new AdresDAOpsql(conn);
        ReizigerDAOPsql rdaopsql = new ReizigerDAOPsql(conn, adaopsql);
        adaopsql.setRdao(rdaopsql);
        testReizigerDAO(rdaopsql);
        testAdresDAO(adaopsql, rdaopsql);
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
        System.out.println(adao.findAll());
        System.out.println("Test findByReiziger");
        System.out.println(adao.findByReiziger(sietske));
        System.out.println("Test update");
        bijlstraat.setHuisnummer("8");
        System.out.println(adao.update(bijlstraat));
        System.out.println("Test delete");
        System.out.println(adao.delete(bijlstraat));
        System.out.println(rdao.delete(sietske));



    }



}
