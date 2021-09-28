package school;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{

    Connection conn;

    //constructor
    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    //methoden
    @Override
    public boolean save(Product prod) throws SQLException {
        Statement st = conn.createStatement();
        try {
            st.execute(String.format("INSERT INTO public.product(product_nummer, naam, beschrijving, prijs) VALUES (%s, '%s', '%s', %s);", prod.getProduct_nummer(), prod.getNaam(), prod.getBeschrijving(), prod.getPrijs()));
            for (OVChipkaart ovc: prod.getKaarten()
                 ) {
                st.execute(String.format("INSERT INTO public.ov_chipkaart_product(product_nummer, kaart_nummer) VALUES (%s, %s);", prod.getProduct_nummer(), ovc.getKaartnummer()));
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public boolean update(Product prod) throws SQLException {
        Statement st = conn.createStatement();
        System.out.println(prod.toString());
        try {
            st.execute(String.format("UPDATE public.product SET  naam='%s', beschrijving = '%s', prijs = %s  WHERE product_nummer=%s", prod.getNaam(), prod.getBeschrijving(), prod.getPrijs(), prod.getProduct_nummer()));

            st.execute(String.format("SELECT kaart_nummer FROM public.ov_chipkaart_product WHERE product_nummer=%s", prod.getProduct_nummer()));
            for (OVChipkaart ovc: prod.getKaarten()
            ) {
                //haal de ID's op van de kaarten die een link hebben met dit product
                ResultSet rs = st.getResultSet();
                ArrayList<Integer> dbLinks = new ArrayList<Integer>();
                while(rs.next()){
                    dbLinks.add(rs.getInt(1));
                }
                //Pak de ID's die een link met dit product horen te hebben
                ArrayList<Integer> prodLinks = new ArrayList<Integer>();
                for(OVChipkaart k: prod.getKaarten()){
                    prodLinks.add(k.Kaartnummer);
                }
                //voeg de links aan de db toe die er horen te zijn
                for (Integer kaartnummer: prodLinks
                     ) {
                    if (!dbLinks.contains(kaartnummer)){
                        st.execute(String.format("INSERT INTO public.ov_chipkaart_product(product_nummer, kaart_nummer) VALUES (%s, %s);", prod.getProduct_nummer(), kaartnummer));
                    }
                }

                //verwijder de verouderde links uit de db
                for (Integer kaartnummer: dbLinks
                ) {
                    if (!prodLinks.contains(kaartnummer)) {
                        st.execute(String.format("REMOVE FROM public.ov_chipkaart_product WHERE product_nummer = %s AND kaart_nummer = %s", prod.getProduct_nummer(), kaartnummer));
                    }
                }


            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public boolean delete(Product prod) throws SQLException {
        //System.out.println(prod.getProduct_nummer());
        Statement st = conn.createStatement();
        try {
            st.execute(String.format("DELETE FROM public.ov_chipkaart_product WHERE product_nummer = %s ", prod.getProduct_nummer()));
            st.execute(String.format("DELETE FROM public.product where product_nummer = %s", prod.getProduct_nummer()));
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public List<Product> findbyOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        Statement st = conn.createStatement();
        List<Product> outList = new ArrayList<Product>();
        try {
            st.execute(String.format("SELECT product.product_nummer, naam, beschrijving, prijs FROM product INNER JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer WHERE kaart_nummer = %s ", ovChipkaart.getKaartnummer()));
            ResultSet rs = st.getResultSet();
            while (rs.next()){
                Product p = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
                p.addKaart(ovChipkaart);
                outList.add(p);
            }
            return outList;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }


    @Override
    public List<Product> findAll() throws SQLException {
        Statement st = conn.createStatement();
        List<Product> outList = new ArrayList<Product>();
        try {
            st.execute("SELECT product_nummer, naam, beschrijving, prijs FROM product INNER JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer");
            ResultSet rs = st.getResultSet();
            while (rs.next()){
                Product p = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
                outList.add(p);
            }
            return outList;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
