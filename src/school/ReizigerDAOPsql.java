package school;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    //connecties
    private Connection conn;
    AdresDAO adao;
    OVChipkaartDAO odao;

    //methoden
    @Override
    public boolean save(Reiziger reiziger) throws SQLException{
        Statement st = conn.createStatement();
        try{
            System.out.println(" ");
            if (reiziger.getTussenvoegsel() == null || reiziger.getTussenvoegsel().equals("")){
                st.execute(String.format("INSERT INTO public.reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (%s, '%s', NULL, '%s', '%s');", reiziger.getId(), reiziger.getVoorletters(), reiziger.getAchternaam(), reiziger.getGeboortedatum().toString()));
            }
            else{
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
            process(st, output);
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
                process(st, output);
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
            process(st, output);
            return output;
        }
        catch (SQLException e){
            System.out.println( e.toString());
            return null;
        }
    }

    private void process(Statement st, List<Reiziger> output) throws SQLException {
        ResultSet rs = st.getResultSet();
        while(rs.next()){
            Reiziger r = new Reiziger(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), Date.valueOf(rs.getString(5)));
            r.setAdres(adao.findByReiziger(r));
            r.setKaarten((ArrayList<OVChipkaart>) odao.findByReiziger(r));
            output.add(r);
        }
    }

    //getters/setters
    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    public void setAdresDAO(AdresDAO a){this.adao = a;}

    public AdresDAO getAdresDAO() {
        return adao;
    }

    public AdresDAO getAdao() {
        return adao;
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public OVChipkaartDAO getOdao() {
        return odao;
    }

    public void setOdao(OVChipkaartDAO odao) {
        this.odao = odao;
    }

}
