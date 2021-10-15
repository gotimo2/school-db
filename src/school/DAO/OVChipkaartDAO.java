package school.DAO;

import school.Domein.OVChipkaart;
import school.Domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart kaart) throws SQLException;
    public boolean update(OVChipkaart kaart) throws SQLException;
    public boolean delete(OVChipkaart kaart) throws SQLException;
    public List<OVChipkaart> findByReiziger(Reiziger r) throws SQLException;
}
