/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;


import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import mx.unam.unibio.jsonprocessor.datatypes.JSONRecord;
import net.sf.json.JSONArray;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;


/**
 *
 * @author jazmin
 */
public class DataBase {

    private JSONRecord metadata;
    private Connection DBConnection;
    private DataStore DBConnectionGis;
    private Statement sql;

    

    private String dbName;
    private String dbMetadata;
    private String dbUser;
    private String dbPassword;

    private String dbTypel;
    private String dbPort;
    private String dbJDBC;
    private String dbURL;
    
    private String urlDWMS;
    private String urlWMS;

    private Boolean connectionDB;
    private Boolean connectionDBgtools;



    //private Map ;

    Properties configFile;// = new Properties();



    public DataBase()throws IOException{
        configFile = new Properties();
        
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
        //Main.print(path);
       // FileInputStream fis = new FileInputStream(new java.io.File( "/"+path + "/casa.properties"));
       /*FileInputStream fis = new FileInputStream(new java.io.File( "/"+path + "/config.properties"));
       configFile.load(fis);
        fis.close()
     */
     
        configFile.load( this.getClass().getClassLoader().getResourceAsStream("config.properties") );

       ;
       // Main.print(configFile.getProperty("DB_METADATA"));
    }

    public Boolean connectDB(){

        //System.out.println("ffffffffffffff");
        dbName = configFile.getProperty("DB_NAME");
        dbMetadata = configFile.getProperty("DB_METADATA");
        dbUser = configFile.getProperty("DB_USER");
        dbPassword = configFile.getProperty("DB_PASSWORD");

        dbTypel = configFile.getProperty("DB_TYPE");
        dbPort =  configFile.getProperty("DB_PORT");
        dbJDBC = configFile.getProperty("DB_JDBC");
        dbURL = configFile.getProperty("DB_URL");

        urlDWMS = configFile.getProperty("URL_DWNS");
        urlWMS = configFile.getProperty("URL_WMS");

        String url = "jdbc:"+dbJDBC+"://"+dbURL+"/"+dbName;
               url = "jdbc:postgresql://10.1.6.31/geodatabase";
        connectionDB = false;
        try {
           // DBConnection = DriverManager.getConnection(url, dbUser, dbPassword);
            DBConnection = DriverManager.getConnection(url, "dba", "PJdiU");
            sql = DBConnection.createStatement();
            connectionDB = true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }


        return connectionDB;
    }

    public Boolean connectDBgtools(){
 
        Map params = new HashMap();
        params.put("dbtype", dbTypel);
        params.put("host", dbURL);
        params.put("port", new Integer(dbPort));
        params.put("database", dbName);
        params.put("user", dbUser);
        params.put("passwd", dbPassword);

        try {
            DBConnectionGis = DataStoreFinder.getDataStore(params);
            connectionDBgtools = true;
        } catch (IOException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            connectionDBgtools = false;
        }
        
        return connectionDBgtools;
    }


    public JSONArray columnsTable2JSON(String nameTable) throws SQLException, Exception{
       //   Main.print(this.dbPassword+"ffffffffffffffffffffff"+this.dbName);
       ResultSet columns = getColumnsShape(nameTable);
       JSONArray jArrayColumns = new JSONArray();
       while( columns.next() ){
           jArrayColumns.add(columns.getString(1));
//           Main.print("jarry "+columns.getString(1));
       }
       return jArrayColumns;
    }

    public ResultSet getColumnsShape(String idShape)throws SQLException{
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name = '"+idShape+"';";
        return sql.executeQuery(query);
    }

    public ResultSet getMetadataShape(String idShape) throws SQLException{
        String query = "SELECT * FROM metadatos.nacion WHERE title = '"+idShape+"';";
        return sql.executeQuery(query);
    }


    public Vector<String> getAttributsShapeFilter(String idShape, String attribute, String filter)throws SQLException{
        String query = "SELECT distinct "+attribute+" FROM "+idShape+" WHERE "+filter+" ;";
        //Main.print("query", query);
        ResultSet rs = sql.executeQuery(query);
        Vector<String> attributs = new Vector();
        while (rs.next()){
            attributs.add(rs.getString(1));
        }
        return attributs;

    }


      public ArrayList<String> daAtributos(String seleccionador, String idShape,
                                                String attribute, String filter)
                                                throws SQLException{
        String query = "SELECT "+seleccionador+" "
                                 +attribute
                                 + " FROM "
                                 +idShape
                                 +" WHERE "
                                 +filter+" ;";
        //Main.print("query", query);
        ResultSet rs = sql.executeQuery(query);
        
        ArrayList<String> arreglo = new ArrayList<String>();
        while(rs.next()){
            arreglo.add(rs.getString(1));
        }
        return arreglo;
    }


   public ArrayList<String> daAtributos(String seleccionador, String idShape,
                                                String attribute)
                                                throws SQLException{
        String query = "SELECT "+seleccionador+" "
                                 +attribute
                                 + " FROM "
                                 +idShape
                                 +" ;";
        //Main.print("query", query);
        ResultSet rs = sql.executeQuery(query);

        ArrayList<String> arreglo = new ArrayList<String>();
        while(rs.next()){
            arreglo.add(rs.getString(1));
        }
        return arreglo;
    }


    public Vector<String> getAttributShape(String idShape, String attribute) throws SQLException{
        String query = "SELECT distinct "+attribute+" FROM "+idShape+";";
        ResultSet rs = sql.executeQuery(query);
        Vector<String> attributs = new Vector();
        while (rs.next()){
            attributs.add(rs.getString(1));
        }
        return attributs;
    }

    public JSONRecord getMetadataShapes() throws SQLException, Exception{
        String query = "select  title,\"title.alternative\",description,subject" +
                       ",\"coverage.description\",\"coverage.x.min\",\"coverage.x.max\",\"coverage.y.min\"" +
                        ",\"coverage.y.max\",\"coverage.bbox\", creator" +
                       ", EXTRACT(YEAR FROM date) as date" +
                      ",format,type,source" +
                      ",\"right\"" +
                      ",relation" +
                       ",\"coverage.scale\",\"coverage.spatial\",language,url,pview" +
                        " from metadatos.nacional;";
       // Main.print(query);
        ResultSet rs = sql.executeQuery(query);
        ArrayList<JSONRecord> metaList = JSONRecord.fromResultSet(rs, false);
        for ( JSONRecord element : metaList ){
            String idShape = element.getString("title");
            query = "SELECT geometrytype(the_geom) from "+idShape+" limit 1;";
            rs =  sql.executeQuery(query);
            rs.next();
            String shpeType = rs.getString("geometrytype");
            element.put("coverage.geom",shpeType);
            //Main.print();
//            Main.print("element: "+element.getString("title"));

        }
        JSONRecord meta = new JSONRecord();
        meta.put("metadata", metaList.toString());
        return meta;
    }

    public JSONRecord capaToJSON(String idShape) throws SQLException, Exception{
        JSONArray columns = columnsTable2JSON(idShape);
        JSONRecord infoShape = new JSONRecord();
        infoShape.put("id", idShape);
        infoShape.put("url", "http://vgeo:8080");
        infoShape.put("urlwms","http://vgeo:8080/geoserver/wms?");
        infoShape.put("colums", columns);
        return infoShape;
    }

   // public FeatureSource

    public FeatureSource getShape(String idShape) throws IOException{
       // Main.print("idSHAPE "+idShape);
        FeatureSource a = DBConnectionGis.getFeatureSource(idShape);
        //Main.print("idSHAPE "+idShape);
        return a;
    }

    /*TODO CONTINUAL E METODO*/
    public JSONRecord datosCapa(String idCapa) throws SQLException{
        ResultSet rs = getColumnsShape(idCapa);
        String columnas ="";
        while( rs.next() ){
            String nombreColumna = rs.getString(0);
            columnas += nombreColumna != "the_geom" ? nombreColumna : "";
        }
        columnas = columnas.substring(0, columnas.length()-1);
        String query = "SELECT "+columnas+" FROM "+idCapa+";";
        rs = sql.executeQuery(query);
        return null;
    }

    public void close(){
        try {
            this.DBConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public boolean existAttribut(String idShape, String attribut){
        ResultSet rs;
        boolean exist = false;
        try {
            rs = this.getColumnsShape(idShape);
                while ( rs.next() ){
                if ( rs.getString("column_name").equals(attribut) ){
                    exist = true;
                    break;
                }
           }
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exist;  
    }


    public boolean existShape(String idShape){
        String query = "SELECT table_name FROM information_schema.tables where table_name='"+idShape+"';";
        //Main.print("Query "+query);
        boolean exist = false;
        try {
            ResultSet rs = sql.executeQuery(query);
            //Main.print("renglones ", rs.getRow());
            boolean siguiente = rs.next();
            if ( siguiente ){
                String table = rs.getString("table_name");
                if ( table.equals(idShape) )
                exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exist;
    }


    /*metadatos = new JSONRecord();
        Connection db;
         try {

            db = DriverManager.getConnection("jdbc:postgresql://10.1.6.31/geodatabase", "dba", "PJdiU");
            if (db != null)
		    System.out.println("me conecte sisisis :D");
		  else
		    System.out.println("chale");

		Statement sql = db.createStatement();

		ResultSet results = sql.executeQuery("select  title,\"title.alternative\",description,subject" +
                       ",\"coverage.description\",\"coverage.x.min\",\"coverage.x.max\",\"coverage.y.min\"" +
                        ",\"coverage.y.max\",\"coverage.bbox\", creator" +
                       ", EXTRACT(YEAR FROM date) as date" +
                      ",format,type,source" +
                      ",\"right\"" +
                      ",relation" +
                       ",\"coverage.scale\",\"coverage.spatial\",language,url,pview" +
                        " from metadatos.nacional;");

               String arregloCapasJson = "";

               JSONArray a = new JSONArray();
                if (results != null){

                    ArrayList<JSONRecord> arreglo = JSONRecord.fromResultSet(results, false);

                     System.out.println(arreglo);

                    metadatos.put("capas", arreglo.toString());
		}else{
                    metadatos.put("capas", "nanai");
                }
        } catch (SQLException ex) {
            Logger.getLogger(Metadatos.class.getName()).log(Level.SEVERE, null, ex);
        }*/

}
