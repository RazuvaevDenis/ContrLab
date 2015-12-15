package netcracker;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Denis on 14.12.2015.
 */
public class Master implements Runnable {

    private Car current_car;
    private Service service;
    public static final Logger log=Logger.getLogger(Master.class.getName());

    public Master(Service service){
        this.service=service;
    }

    public void setCurrent_car(Car current_car){
        this.current_car=current_car;
    }

    public void XMLCreate(Car car){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            Document doc = factory.newDocumentBuilder().newDocument();
            Element root = doc.createElement(car.getMark());
            doc.appendChild(root);
            Element item1 = doc.createElement("Name");
            item1.setTextContent(car.getName());
            root.appendChild(item1);
            Element item2 = doc.createElement("Owner");
            item2.setTextContent(car.getOwner());
            root.appendChild(item2);
            Element item3 = doc.createElement("Handling_time");
            item3.setTextContent(Integer.toString(car.getHandling_time()));
            root.appendChild(item3);
            File path=new File("xml");
            if (!path.exists()) {
                path.mkdir();
            }
            File file = new File("xml/NewXML"+car.getName()+ car.getOwner()+".xml");
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(file));
        } catch (ParserConfigurationException e) {
            log.log(Level.ERROR,"Error." + e.getMessage(), e);
        } catch (TransformerConfigurationException e) {
            log.log(Level.ERROR,"Error." + e.getMessage(), e);
        } catch (TransformerException e) {
            log.log(Level.ERROR,"Error." + e.getMessage(), e);
        }
    }

    public void ConnectionDB(Car car){
        String url = "jdbc:mysql://localhost:3306/jdbc";
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/jdbc.properties")){
            property.load(fis);
        } catch (IOException e) {
            log.log(Level.ERROR,"Error." + e.getMessage(), e);
        }
        try (Connection connection = DriverManager.getConnection(url, property);
             Statement st=connection.createStatement();
             PreparedStatement pst=connection.prepareStatement("insert into " +car.getMark()+ "(name,owner,handling_time) VALUES(?,?,?)")) {
            Class.forName("com.mysql.jdbc.Driver");
            st.execute("create table if not exists "+car.getMark()+
                    "(car_id int(7) not null auto_increment, name varchar(1000) not null, owner varchar(1000) not null," +
                    "handling_time int(10) not null, constraint "+car.getMark()+"_id_pk primary key(car_id))");
            pst.setString(1, car.getName());
            pst.setString(2,car.getOwner());
            pst.setInt(3,car.getHandling_time());
            pst.executeUpdate();
            pst.execute("commit");
        } catch (SQLException e) {
            log.log(Level.ERROR,"Error with error code " +  e.getErrorCode()+"." + e.getMessage(),e);
        } catch (ClassNotFoundException e) {
            log.log(Level.ERROR,"Error." + e.getMessage(),e);
        }
    }

    @Override
    public void run() {
        while(true){
            Car car=service.Pop();
            try {
                Thread.sleep(car.getHandling_time());
                switch(car.getType_of_record()){
                    case Car.CONSOLETYPE:
                        log.log(Level.INFO, "Car repaired - " + car.getMark() + " " + car.getName() + " " + car.getOwner());
                        break;
                    case Car.DBTYPE:
                        ConnectionDB(car);
                    case Car.XMLTYPE:
                        XMLCreate(car);
                }

            } catch (InterruptedException e) {
                log.log(Level.ERROR,"Error" + e.getMessage(),e);
            }
        }
    }
}
