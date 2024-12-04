
package it2d.patenio.bdcs;

import java.util.Scanner;

public class TypeofDocument {
    public void addTypeofDocument() {

        Scanner sc = new Scanner(System.in);
        String response;
        boolean exit = true;

        do {
            System.out.println("\nTYPE OF DOCUMENT MENU:");
            System.out.println("1. ADD");
            System.out.println("2. VIEW");
            System.out.println("3. UPDATE");
            System.out.println("4. DELETE");
            System.out.println("5. EXIT");

            System.out.print("Enter Action: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 5: ");
                sc.next();
            }
            
            int action = sc.nextInt();
            TypeofDocument dc = new TypeofDocument();

            switch (action) {
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
                    String resp = sc.next();
                    
                    while (!(resp.equalsIgnoreCase("yes") || resp.equalsIgnoreCase("no"))) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no': ");
                    resp = sc.next();
                }
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    break;

               default:
                    System.out.print("Invalid option. Please try again: ");
                    break;
            }

            if (action >= 1 && action <= 4) {
                System.out.print("Do you want to make another transaction? (yes/no): ");
                response = sc.next();

                while (!(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no"))) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no': ");
                    response = sc.next();
                }

              
                if (response.equalsIgnoreCase("no")) {
                    exit = false;
                }
            }

        } while (exit); 

        System.out.print("Thank you for using!");
    }

    public void addDoctype() {
    Scanner sc = new Scanner(System.in);

    config conf = new config();

    
    String dname;
    while (true) {
        System.out.print("Document Name: ");
        dname = sc.nextLine();
        
     
        if (!dname.isEmpty() && dname.matches("[a-zA-Z ]+")) {
            break; 
        } else {
            System.out.print("Invalid input. Document name must only contain alphabetic characters and spaces: ");
        }
    }

  
      double tprice = -1; 
    while (tprice <= 0) {
        System.out.print("Document Price: ");
        
        if (sc.hasNextDouble()) {
            tprice = sc.nextDouble();

            if (tprice <= 0) {
                System.out.print("Price must be greater than zero. Please enter a valid price: ");
            }
        } else {
            System.out.print("Invalid input. Please enter a valid numeric price: ");
            sc.next();
        }
    }

    // SQL insertion
    String sql = "INSERT INTO tbl_tdocument (t_name, t_price) VALUES (?, ?)";
    conf.addDtype(sql, dname, tprice);
}


    public void viewDoctype() {
        config conf = new config();

        String docuQuery = "SELECT * FROM tbl_tdocument";
        String[] docuHeaders = {"Document Type", "Document Name", "Document Price"};
        String[] docuColumns = {"t_id", "t_name", "t_price"};

        conf.viewDtype(docuQuery, docuHeaders, docuColumns);
    }
private void updateDoctype() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    int id = -1;
    while (id <= 0) {
        System.out.print("Enter Document ID to update: ");
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid Document ID: ");
            sc.next(); 
        }
        id = sc.nextInt();

        if (!conf.documentExists(id)) {
            System.out.print("Document ID " + id + " does not exist: ");
            id = -1;
        }
    }


    double price = -1;
    while (price <= 0) {
        System.out.print("New Price: ");
        while (!sc.hasNextDouble()) {
            System.out.print("Invalid! Please enter a valid price: ");
            sc.next();
            System.out.print("New Price: ");
        }
        price = sc.nextDouble();
        if (price <= 0) {
            System.out.println("Price must be greater than zero. Please enter a valid price: ");
        }
    }

    String sql = "UPDATE tbl_tdocument SET t_price = ? WHERE t_id = ?";
    conf.updateDtype(sql, price, id); // parameterize the name in the question mark
}


   public void deleteDoctype() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    
    int id = -1;
    
    while (true) {
        System.out.print("Enter Document ID to delete: ");
        
        while (!sc.hasNextInt()) {
            System.out.print("Invalid! Please enter a valid Document ID.");
            sc.next();  
            System.out.print("Enter Document ID to delete: ");
        }
        
        id = sc.nextInt();

        if (id <= 0) {
            System.out.print("Invalid Document ID! Please enter a positive integer.");
            continue; 
        }

        if (!conf.documentExists(id)) {
            System.out.print("Document ID " + id + " does not exist. Enter a valid resident ID.");
            continue; 
        }

        String sql = "DELETE FROM tbl_tdocument WHERE t_id = ?";
        conf.deleteDtype(sql, id);
        System.out.print("Document with ID " + id + " has been deleted.");
        
        break;
    }



    String sql = "DELETE FROM tbl_tdocument WHERE t_id = ?";
    conf.deleteDtype(sql, id);
    System.out.println("Document with ID " + id + " has been deleted.");
}
}