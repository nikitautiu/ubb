package Repository;


import Domain.Rental;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.Integer.parseInt;

/**
 * Created by camelia on 11/21/2016.
 */
public class RentalXMLRepo extends RentalRepo {
    private String fileName;

    public RentalXMLRepo(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    @Override
    public void add(Rental rental) {
        super.add(rental);
        writeToFile();  // save the changes
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
        writeToFile();  // save the changes
    }

    public void loadData()  {
        Document document = loadDocument();
        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType()==Node.ELEMENT_NODE) {   //node instanceof Element
                Element element = (Element) node;
                Rental rental = createRental(element);
                add(rental);
            }
        }
    }

    public void writeToFile() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("rentals");
            doc.appendChild(rootElement);
            for (Rental x:getAll())
            {
                Element rental = doc.createElement("rental");
                rootElement.appendChild(rental);
                rental.setAttribute("id", x.getId().toString());
                rental.setAttribute("movieId", x.getMovieId().toString());
                rental.setAttribute("clientId", x.getClientId().toString());

            }
            saveDocument(doc);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }


    private static void appendStudentElement(Document doc, String tagName, String textNode, Element studentNode)
    {
        Element element=doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textNode));
        studentNode.appendChild(element);
    }
    private Rental createRental(Element element) {
        String id=element.getAttributeNode("id").getValue();
        String movieId=element.getAttributeNode("movieId").getTextContent();
        String clientId=element.getAttributeNode("clientId").getTextContent();

        return new Rental(parseInt(id), parseInt(movieId), parseInt(clientId));
    }

    Document loadDocument() {
        try {
            //File inputFile = new File(super.fileName);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            Document doc = null;
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(new FileInputStream(fileName));
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
            throw new RepositoryException("Corrupted file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void saveDocument(Document doc) {
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("File saved!");
    }

}
