package school.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import school.Domein.OVChipkaart;
import school.Domein.Product;
import school.Domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {

    private Session session;
    public ProductDAOHibernate(Session session){this.session = session;}

    @Override
    public boolean save(Product product) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Product product) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.update(product);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        try {
            Transaction tx = session.beginTransaction();
            session.delete(product);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Product> findbyOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        return ovChipkaart.getProducten();
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> producten = session.createQuery("from Product ").list();
        return producten;
    }
}
