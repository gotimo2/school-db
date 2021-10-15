package school;

import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import school.DAO.*;
import school.Domein.Adres;
import school.Domein.OVChipkaart;
import school.Domein.Product;
import school.Domein.Reiziger;

import javax.persistence.metamodel.EntityType;
import java.sql.SQLException;
import java.util.List;

public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {

        Session session = Main.getSession();

        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate(session);
        AdresDAO adresDAO = new AdresDAOHibernate(session);
        ProductDAO productDAO = new ProductDAOHibernate(session);
        OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOHibernate(session, productDAO);

        testFetchAll();
        testReizigerDAO(reizigerDAO);
        testAdresDAO(adresDAO, reizigerDAO);
        testOVChipkaartDAO(ovChipkaartDAO, reizigerDAO);
        testProductDAO(productDAO, ovChipkaartDAO, reizigerDAO);


    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }



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
        Reiziger sietske = new Reiziger("S", "", "Boers", java.sql.Date.valueOf(gbdatum));

        //test de DAO
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        System.out.println(rdao.save(sietske));
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");

        System.out.println("[Test] Update, achternaam moet Boersen zijn");
        sietske.setAchternaam("Boersen");
        System.out.println(rdao.update(sietske));
        System.out.println(rdao.findByGbDatum(gbdatum));

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
        Reiziger sietske = new Reiziger("S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.save(sietske);

        //geef een adres aan de reiziger
        Adres bijlstraat = new Adres(77, "1402RD", "119A", "Bijlstraat", "Bussum", sietske);


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
        Reiziger reiziger = rdao.findByID(3);
        System.out.println("haal reiziger uit DB");
        System.out.println(reiziger);
        reiziger.registreerKaart(1,300.0, java.sql.Date.valueOf("2023-04-04"));
        OVChipkaart kaart = reiziger.getKaarten().get(1);

        //test DAO
        System.out.println("\n[Test] save kaart, moet true geven");
        System.out.println(odao.save(kaart));

        System.out.println("\n[Test] update kaart, moet true geven en in de volgende test moet een kaart 100.1 euro hebben");
        kaart.setSaldo(100.1);
        System.out.println(odao.update(kaart));

        System.out.println("\n[Test] findbyreiziger, van lubben moet twee kaarten laten zien");

        List<OVChipkaart> kaarten = odao.findByReiziger(reiziger);


        for (OVChipkaart o : kaarten){
            System.out.println(o.toString());
        }

        System.out.println("\n[Test] delete kaart, moet true geven");
        System.out.println(odao.delete(kaart));

    }

    public static void testProductDAO(ProductDAO pdao, OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n--------------TEST ProductDAO-------------");

        //maak Product
        Product pro = new Product("Snelle trein pas", "pas die trein snel laat gaan", 10.0);
        Product pro2 = new Product("langzame trein pas", "pas die trein langzaam laat gaan", 10.0);


        //maak kaart
        System.out.println("[Test] haal reiziger uit DB");
        Reiziger reiziger = rdao.findByID(3);
        System.out.println(reiziger);

        System.out.println("[Test] Registreer kaart op reiziger");
        reiziger.registreerKaart(1,300.0, java.sql.Date.valueOf("2023-04-04"));
        OVChipkaart kaart = reiziger.getKaarten().get(1);
        System.out.println(kaart);

        System.out.println("[Test] Voeg product toe aan kaart");
        kaart.addProduct(pro);
        System.out.println(pro);
        System.out.println(kaart.getProducten());

        //test DAO
        System.out.println("\n[Test] save kaart met product, moet true geven");
        System.out.println(odao.save(kaart));

        System.out.println("\n[Test] save product, moet true geven");
        System.out.println(pdao.save(pro2));

        System.out.println("\n[Test] update kaart met producten, moet true geven en in de volgende test moet kaar 10382 100.1 euro hebben");
        kaart.verwijderProduct(pro);
        kaart.addProduct(pro2);
        System.out.println(odao.update(kaart));

        System.out.println("\n[Test] findbyovchipkaart, moet langzame trein laten zien");
        for (Product p : pdao.findbyOVChipkaart(kaart)){
            System.out.println(p.toString());
        }

        System.out.println("\n[Test] delete product, moet true geven");
        System.out.println(pdao.delete(pro));

        System.out.println("[Test] Kaart wordt nog verwijderd");
        System.out.println(odao.delete(kaart));

    }
}
