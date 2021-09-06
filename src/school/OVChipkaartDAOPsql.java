package school;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    Connection conn;

    //constructor
    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    //methoden
    @Override
    public boolean save(OVChipkaart kaart) throws SQLException {
        Statement st = conn.createStatement();
        try{
            st.execute(String.format("INSERT INTO public.ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (%s, '%s', %s, %s, %s);", kaart.getKaartnummer(), kaart.getGeldigTot(), kaart.getKlasse(), kaart.getSaldo(), kaart.getReiziger().getId()));
            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart kaart) throws SQLException {
        Statement st = conn.createStatement();
        try{
            st.execute(String.format("UPDATE public.ov_chipkaart SET geldig_tot = '%s', klasse = %s, saldo = %s, reiziger_id = %s WHERE kaart_nummer = %s", kaart.getGeldigTot(), kaart.getKlasse(), kaart.getSaldo(), kaart.getReiziger().getId(), kaart.getKaartnummer()));
            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart kaart) throws SQLException {
        Statement st = conn.createStatement();
        try{
            st.execute(String.format("DELETE FROM public.ov_chipkaart WHERE kaart_nummer = %s;", kaart.getKaartnummer()));
            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger r) throws SQLException {
        Statement st = conn.createStatement();
        try{
            List<OVChipkaart> output = new ArrayList<OVChipkaart>();
            st.execute(String.format("SELECT * FROM public.ov_chipkaart WHERE reiziger_id = %s;", r.getId()));
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                output.add(new OVChipkaart(r, Double.valueOf(rs.getString(4)), java.sql.Date.valueOf(rs.getString(2)), Integer.parseInt(rs.getString(3)), Integer.parseInt(rs.getString(1))));
            }
            return output;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return null;
        }
    }
}
