package school;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;
    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException{
        Statement st = conn.createStatement();
        try{
            System.out.println(" ");
            if (reiziger.getTussenvoegsel() == null || reiziger.getTussenvoegsel().equals("")){
                //System.out.println(String.format("INSERT INTO public.reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (%s, '%s', NULL, '%s', '%s');", reiziger.getId(), reiziger.getVoorletters(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString()));
                st.execute(String.format("INSERT INTO public.reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (%s, '%s', NULL, '%s', '%s');", reiziger.getId(), reiziger.getVoorletters(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString()));
            }
            else{
                //System.out.println(String.format("INSERT INTO public.reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (%s, '%s', '%s', '%s', '%s');", reiziger.getId(), reiziger.getVoorletters(), reiziger.getTussenvoegsel(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString()));
                st.execute(String.format("INSERT INTO public.reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (%s, '%s', '%s', '%s', '%s');", reiziger.getId(), reiziger.getVoorletters(), reiziger.getTussenvoegsel(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString()));
            }

            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        Statement st = conn.createStatement();
        try{
            System.out.println(" ");
            if (reiziger.getTussenvoegsel() == null || reiziger.getTussenvoegsel().equals("")){
                st.execute(String.format("UPDATE public.reiziger SET voorletters='%s', tussenvoegsel=NULL, achternaam='%s', geboortedatum='%s' WHERE reiziger_id=%s;", reiziger.getVoorletters(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString(), reiziger.getId()));
            }
            else{
                st.execute(String.format("UPDATE public.reiziger SET voorletters='%s', tussenvoegsel='%s', achternaam='%s', geboortedatum='%s' WHERE reiziger_id=%s;", reiziger.getVoorletters(), reiziger.getTussenvoegsel(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString(), reiziger.getId()));
            }
            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        Statement st = conn.createStatement();
        try{
            st.execute(String.format("DELETE FROM public.reiziger WHERE reiziger_id=%s", reiziger.getId()));
            return true;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return false;
        }
    }

    @Override
    public Reiziger findByID(int id) throws SQLException {
        Statement st = conn.createStatement();
        try{
            List<Reiziger> output = new ArrayList<Reiziger>();
            st.execute("SELECT * FROM public.reiziger WHERE reiziger_id=" + id);
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                output.add(new Reiziger(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), Date.valueOf(rs.getString(5))));
            }
            return output.get(0);
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) throws SQLException {
            Statement st = conn.createStatement();
            try{
                List<Reiziger> output = new ArrayList<Reiziger>();
                st.execute(String.format("SELECT * FROM public.reiziger WHERE geboortedatum='%s';", datum));
                ResultSet rs = st.getResultSet();
                while(rs.next()){
                    output.add(new Reiziger(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), Date.valueOf(rs.getString(5))));
                }
                return output;
            }
            catch (SQLException e){
                System.out.println( e.toString());
                return null;
            }
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        Statement st = conn.createStatement();
        try{
            List<Reiziger> output = new ArrayList<Reiziger>();
            st.execute("SELECT * FROM public.reiziger");
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                output.add(new Reiziger(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), Date.valueOf(rs.getString(5))));
            }
            return output;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return null;
        }
    }
}
