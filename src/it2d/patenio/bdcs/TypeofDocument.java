
package it2d.patenio.bdcs;

import java.util.Scanner;


public class TypeofDocument {
    public void addTypeofDocument(){
    
        Scanner sc = new Scanner(System.in);
        String response;
        boolean exit=true;
        
        do{
        System.out.println("1. ADD");
        System.out.println("2. VIEW");
        System.out.println("3. UPDATE");
        System.out.println("4. DELETE");
        System.out.println("5. EXIT");
        
        
        System.out.print("Enter Action: ");
        int action= sc.nextInt();
        TypeofDocument dc=new TypeofDocument();
        
        switch(action){
            case 1:
                dc.addDoctype();               
                break;
                
            case 2:
                dc.viewDoctype();                 
                break;
                
            case 3:
                dc.viewDoctype();   
                dc.updateDoctype();
                dc.viewDoctype();
                break;
                
            case 4:
                dc.viewDoctype();
                dc.deleteDoctype();
                dc.viewDoctype();
                break;

            case 5:
                   System.out.print("Exiting...Are you sure? (yes/no): ");
                    String resp= sc.next();
                    if(resp.equalsIgnoreCase("yes")){
                        exit=false;
                    }
                    break;

             default:
                    System.out.println("Invalid option. Please try again.");
                    break;
         }    
         System.out.print("Do you want to continue? (yes/no): ");
         response=sc.next();
        }while(response.equalsIgnoreCase("yes"));
         System.out.print("Thank you, See you soonest!\n"); 

    }

     public void addDoctype(){
        Scanner sc = new Scanner(System.in);
        
        config conf = new config();
        
        System.out.print("Document Name: ");
        String dname = sc.nextLine();
        
        System.out.print("Document Price: ");
        double tprice = sc.nextDouble();
        

        String sql = "INSERT INTO tbl_tdocument (t_name, t_price) VALUES (?, ?)";


        conf.addDtype(sql, dname, tprice);

    }
      
     private void viewDoctype() {
         config conf= new config();
         
        String docuQuery = "SELECT * FROM tbl_tdocument";
        String[] docuHeaders = {"Document Name", "Document Price"};
        String[] docuColumns = {"t_name", "t_price"};

        conf.viewDtype(docuQuery, docuHeaders, docuColumns);
    }
    
     private void updateDoctype(){

        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Resident ID to update: ");
        int id = sc.nextInt();

        System.out.print("New Price: ");
        double price = sc.nextDouble() ;
       
        

        String sql = "UPDATE tbl_tdocument SET t_price = ? WHERE t_id = ?";
        conf.updateResident(sql, price, id); //parameterize ang name sa question mark
    }
      public void deleteDoctype() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Resident ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM tbl_tdocument WHERE r_id = ?";
        conf.deleteResident(sql, id);
    }
      
}
    


    


    

