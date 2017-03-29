import java.util.Map;

public class Main {



    public static void main(String[] args) {
        BeanStoragee bs = BeanStoragee.getInstance();
        Map<String, org.w3c.dom.Node> beansMap;
        try {

            Object obj1 = bs.getBean("bean1", "myxml.xml"); // Должен вернуть объект класса Person с заполненными полями
            Object obj2 = bs.getBean("bean2", "myxml.xml"); // Должен вернуть объект класса Doctor с заполненными полями

            System.out.println(obj1.toString());
            System.out.println(obj2.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
