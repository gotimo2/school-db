package school.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import school.Domein.Adres;
import school.Domein.OVChipkaart;
import school.Domein.Product;
import school.Domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO{

    private Session session;
    private ProductDAO productDAO;

    public OVChipkaartDAOHibernate(Session session, ProductDAO productDAO){this.session = session; this.productDAO = productDAO;}

    @Override
    public boolean save(OVChipkaart kaart) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.save(kaart);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart kaart) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.update(kaart);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart kaart) throws SQLException {
        try {
        Transaction tx = session.beginTransaction();
        session.delete(kaart);
        tx.commit();
        return true;
    } catch (Exception e) {
        System.out.println(e);
        return false;
    }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger r) throws SQLException {
        List<OVChipkaart> kaarten = session.createQuery("from OVChipkaart WHERE reiziger_id = " + r.getId()).list();
        return kaarten;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
