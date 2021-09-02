package school;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger) throws SQLException;
    public boolean update(Reiziger reiziger) throws SQLException;
    public boolean delete(Reiziger reiziger) throws SQLException;
    public Reiziger findByID(int id) throws SQLException;
    public List<Reiziger> findByGbDatum(String datum) throws SQLException;
    public List<Reiziger> findAll() throws SQLException;
}
