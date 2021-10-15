package school.DAO;

import school.Domein.OVChipkaart;
import school.Domein.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;
    public boolean update(Product product) throws SQLException;
    public boolean delete(Product product) throws SQLException;
    public List<Product> findbyOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;
    public List<Product> findAll() throws SQLException;
}
