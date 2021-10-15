package school.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import school.Domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    private final Session session;

    public ReizigerDAOHibernate(Session session){
        this.session = session;
    }


    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.save(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.update(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.delete(reiziger);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Reiziger findByID(int id) throws SQLException {
        try{

            Reiziger r = (Reiziger) session.get(Reiziger.class, id);
            return r;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) throws SQLException {
        List<Reiziger> reizigers = session.createQuery(String.format("FROM Reiziger WHERE geboortedatum = TO_DATE('%s', 'yyyy-mm-dd')", datum)).list();
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = session.createQuery("from Reiziger").list();
        return reizigers;
    }
}
