import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class BeanStoragee {
    private static BeanStoragee ourInstance = new BeanStoragee();
    private static   Map<String, Node> beansMap = new HashMap<>();;

    public static BeanStoragee getInstance() {
        return ourInstance;
    }

    private BeanStoragee() {
    }

    private static NodeList returnNodeList (String compile, Node root) throws XPathException {
        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();
        XPathExpression expr = null;
        try {
            expr = xpath.compile(compile);
            NodeList nodes = (NodeList) expr.evaluate(root, XPathConstants.NODESET);
            return nodes;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new XPathException("method returnNodeList");
        }
    }

    private static Map<String, Node> loadDescription(String fileName) throws Exception{
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            Node root = document.getDocumentElement();
            NodeList nodes = returnNodeList("bean", root);

            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                String id = ((Element) n).getAttribute("id");
                beansMap.put(id, n);
            }
            return beansMap;
        } catch  (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public Object getBean(String beanName, String fileName) throws Exception{

        Node n = loadDescription(fileName).get(beanName);

        try {
            Class bean = Class.forName(((Element) n).getAttribute("class"));
            Object newBean = bean.newInstance();

            NodeList nodes = returnNodeList("property [not(text())]", n);


            for (int i = 0; i < nodes.getLength(); i++) {
                Node n1 = nodes.item(i);
                NamedNodeMap attrs = n1.getAttributes();
                for (int j = 0; j < attrs.getLength(); j++) {
                        Attr attribute = (Attr) attrs.item(j);
                    Field f = bean.getDeclaredField(attribute.getValue());
                    f.setAccessible(true);
                    j++;
                    Attr attribute2 = (Attr) attrs.item(j);
                    f.set(newBean, attribute2.getValue());
                }
            }
                    return newBean;

                } catch(Exception e){
                    e.printStackTrace();
                    throw new Exception();
                }
            }
       }

