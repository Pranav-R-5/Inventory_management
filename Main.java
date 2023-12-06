import java.util.*;
import java.io.*;


class Product{
    int product_Id;
    String product_Name;
    int product_price;
    static int product_quantity;

    Product(){
        System.out.println("product constructer called");
    }

    Product(int id,String name,int price,int quantity){
        this.product_Id=id;
        this.product_Name=name;
        this.product_price=price;
        this.product_quantity=quantity;
    }

    void prod_details(){
        System.out.println("Id: "+this.product_Id);
        System.out.println("Name: "+this.product_Name);
        System.out.println("Price: "+this.product_price);
        System.out.println("Quantity: "+this.product_quantity);
    }
}

class Inventory extends Product{

    static HashMap<Integer,Product> map=new HashMap<Integer, Product>();

    static void read_file(){
        try {
            File myObj = new File("/Users/pranavasree/IdeaProjects/inventory management/src/input");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);
                String[] prod_details=data.split(" ");
                int prod_id=Integer.parseInt(prod_details[0]);
                String name=prod_details[1];
                int price=Integer.parseInt(prod_details[2]);
                int quantity=Integer.parseInt(prod_details[3]);
                Product pd=new Product(prod_id,name,price,quantity);
                Inventory.add_map(prod_id,pd);

                //System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void add_map(int id,Product p){
        if(map.containsKey(id)){
            System.out.println("product already exists");
        }
        else {
            map.put(id, p);
            System.out.println("Product is being added");
        }
    }

    static void add_product(int id,Product p){
        if(map.containsKey(id)){
            System.out.println("product already exists");
        }
        else {
            map.put(id, p);
            System.out.println("Product is being added");

            try {
                FileWriter writer = new FileWriter("/Users/pranavasree/IdeaProjects/inventory management/src/input",true);
                BufferedWriter bf = new BufferedWriter(writer);
                bf.write(id +" "+ p.product_Name +" "+ p.product_price +" "+ p.product_quantity);
                bf.newLine();
                bf.close();
            } catch (IOException e) {
                System.out.println("error occured");
                e.printStackTrace();
            }
            Inventory.updateLog(p.product_Name+" is added");
     }
    }

    static void updateLog(String log){
        try {
            FileWriter writer = new FileWriter("/Users/pranavasree/IdeaProjects/inventory management/src/logs",true);
            BufferedWriter bf = new BufferedWriter(writer);
            bf.write(log);
            bf.newLine();
            bf.close();
        }catch(IOException e){
            System.out.println("error occured");
            e.printStackTrace();
        }
    }

    static void updatepurchases(String purchases){

        try {
            FileWriter writer = new FileWriter("/Users/pranavasree/IdeaProjects/inventory management/src/purchases",true);
            BufferedWriter bf = new BufferedWriter(writer);
            bf.write(purchases);
            bf.newLine();
            bf.close();
        }catch(IOException e){
            System.out.println("error occured");
            e.printStackTrace();
        }
    }

    static void details(int id){
        if(!map.containsKey(id)){
            System.out.println("No product is in the Inventory with id "+ id);
        }
        else{
            Product p=map.get(id);
            p.prod_details();
            Inventory.updateLog(p.product_Name+" details is viewed");
        }
    }

    static void details(){
        if(map.size()==0){
            System.out.println("Inventory is empty");
        }
        else{
            for(int i:map.keySet()){
                Product p=map.get(i);
                p.prod_details();
            }
            Inventory.updateLog("Inventory details is being viewed");
        }
    }

    static synchronized void purchase(int id,int quantity){

        Product p = map.get(id);
            if (map.containsKey(id)) {
                if (quantity<= p.product_quantity) {
                    // Simulate some processing time for the purchase
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update inventory after successful purchase
                     p.product_quantity -= quantity;

                    System.out.println("Purchased " + quantity + " units of product " + p.product_Name);
                    System.out.println("Remaining quantity: " + p.product_quantity);
                    Inventory.updateLog(quantity + " of " +p.product_Name+" is purchased");
                    Inventory.updatepurchases(quantity + " of " +p.product_Name+" is purchased");
                }
            } else {
                System.out.println("Insufficient stock for product " + p.product_Name);
            }

    }

    static void full_report(){
        if(map.size()==0){
            System.out.println("Inventory is empty");
        }
        else{
            int sum=0;
            for(int i:map.keySet()){
                Product p=map.get(i);
                p.prod_details();
                sum+=p.product_price*p.product_quantity;
            }
            System.out.println(sum);
            Inventory.updateLog("Full price report of inventory is viewed");
        }
    }

    static void specific_report(int id){
        if(!map.containsKey(id)){
            System.out.println("No product is in the Inventory with id "+ id);
        }
        else{
            Product p=map.get(id);
            System.out.println(p.product_price*p.product_quantity);
            Inventory.updateLog("specific price report of inventory is viewed");
        }
    }
}


class PurchaseThread extends Thread {
    private Inventory inventory;
    private int  productId;
    private int quantity;

    public PurchaseThread(Inventory inventory, int productId,int quant) {
        this.inventory = inventory;
        this.productId = productId;
        this.quantity = quant;
    }

    @Override
    public void run() {
        inventory.purchase(productId,quantity);
    }
}

public class Main {

    public static void main(String[] args) {
        Inventory inventory=new Inventory();

        Scanner sc=new Scanner(System.in);
        Inventory.read_file();
        while(true) {
            System.out.println("Enter 1 to add product");
            System.out.println("Enter 2 to purchase product");
            System.out.println("Enter 3 to get product details");
            System.out.println("Enter 4 to get value of the inventory");
            System.out.println("Enter 5 to exit");
            int x = sc.nextInt();
            switch (x) {
                case 1:
                    //ADD PRODUCT
                    System.out.println("Enter product Id: ");
                    int id = sc.nextInt();
                    if(id<0){
                        System.out.println("Id is in negative value");
                        break;
                    }
                    System.out.println("Enter product name: ");
                    String name = sc.next();
                    System.out.println("Enter product price: ");
                    int price = sc.nextInt();
                    if(price<0){
                        System.out.println("price is in negative value");
                        break;
                    }

                    System.out.println("Enter product quantity: ");
                    int quantity = sc.nextInt();
                    if(quantity<0){
                        System.out.println("quantity is in negative value");
                        break;
                    }

                    Product p = new Product(id, name, price, quantity);
                    inventory.add_product(id,p);
                    break;
                case 2:
                    //PURCHASE PRODUCT
                    System.out.println("Enter product Id ");
                    int p_id = sc.nextInt();

                    System.out.println("Enter product quantity");
                    int p_quantity = sc.nextInt();

                    Thread thread1=new PurchaseThread(inventory,p_id,p_quantity);
                    thread1.start();
                    //inventory.purchase(p_id, p_quantity);
                    break;

                case 3:
                    //INVENTORY DETAILS
                    System.out.println("To check full inventory details give 1");
                    System.out.println("To check specific inventory details give 2");

                    int c = sc.nextInt();
                    if(c >2 || c<=0){
                        System.out.println("Incorrect input is given");
                        break;
                    }
                    switch (c) {
                        case 1:
                            inventory.details();
                            break;

                        case 2:
                            System.out.println("enter the id: ");
                            int i = sc.nextInt();
                            inventory.details(i);
                            break;
                    }
                    break;

                case 4:
                    //report
                    System.out.println("to get full inventory value give 1");
                    System.out.println("To get specific inventory value give 2");
                    int d = sc.nextInt();
                    switch (d) {
                        case 1:
                            inventory.full_report();
                            break;

                        case 2:
                            System.out.println("enter the id: ");
                            int specific_id = sc.nextInt();
                            inventory.specific_report(specific_id);
                            break;
                    }
                    break;

                case 5:
                    //exit
                    return;

            }
        }

    }
}
