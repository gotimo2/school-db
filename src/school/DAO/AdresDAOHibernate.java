package school.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import school.Domein.Adres;
import school.Domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO{

    private Session session;
    public AdresDAOHibernate(Session session){this.session = session;}

    @Override
    public boolean save(Adres adres) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.save(adres);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.update(adres);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.delete(adres);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger r) throws SQLException {
        List<Adres> adressen = session.createQuery("from Adres WHERE reiziger_id = " + r.getId()).list();
        if(!adressen.isEmpty()){
           Adres out = adressen.get(0);
           return out;
        }
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> adressen = session.createQuery("from Adres").list();
        return adressen;
    }
}
