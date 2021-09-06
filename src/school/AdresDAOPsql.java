package school;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{

    Connection conn;
    ReizigerDAO rdao;

    public void setReizigerdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }
    public ReizigerDAO getReizigerdao() {
        return rdao;
    }

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        Statement st = conn.createStatement();
        try{
            st.execute(String.format("INSERT INTO public.adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id)VALUES (%s, '%s', '%s', '%s', '%s', %s);", adres.getId(), adres.getPostcode(), adres.getHuisnummer(), adres.getStraat(), adres.getWoonplaats(), adres.getReiziger_id()));
            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        Statement st = conn.createStatement();
        try{
            st.execute(String.format("UPDATE public.adres SET postcode = '%s', huisnummer = '%s', straat = '%s', woonplaats = '%s', reiziger_id = %s WHERE adres_id = %s", adres.getPostcode(), adres.getHuisnummer(), adres.getStraat(), adres.getWoonplaats(), adres.getReiziger_id() ,adres.getId()));
            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        Statement st = conn.createStatement();
        try{
            st.execute(String.format("DELETE FROM public.adres WHERE adres_id = %s;", adres.getId()));
            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger r) throws SQLException {
        Statement st = conn.createStatement();
        try{
            List<Adres> output = new ArrayList<Adres>();
            st.execute(String.format("SELECT * FROM public.adres WHERE reiziger_id='%s';", r.getId()));
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                output.add(new Adres(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6))));
            }
            if (output.isEmpty()) {
                return null;
            }
            return output.get(0);
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return null;
        }
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        Statement st = conn.createStatement();
        try{
            List<Adres> output = new ArrayList<Adres>();
            st.execute(String.format("SELECT * FROM public.adres;"));
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                output.add(new Adres(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString(6))));
            }
            return output;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return null;
        }
    }
}
