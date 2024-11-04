
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
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                sc.next();
                System.out.print("Enter Action: ");
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
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
             if (action >= 1 && action <= 4) {
            System.out.print("Do you want to make another transaction? (yes/no): ");
            response = sc.next();
            if (!response.equalsIgnoreCase("yes")) {
                exit = false;
            }
        }
        } while (exit);
    }

    public void addDoctype() {
        Scanner sc = new Scanner(System.in);

        config conf = new config();

        System.out.print("Document Name: ");
        String dname = sc.nextLine();

        System.out.print("Document Price: ");
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid price.");
            sc.next(); 
            System.out.print("Document Price: ");
        }
        double tprice = sc.nextDouble();

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

        System.out.print("Enter Document ID to update: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid Document ID.");
            sc.next(); 
            System.out.print("Enter Document ID to update: ");
        }
        int id = sc.nextInt();

        System.out.print("New Price: ");
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid price.");
            sc.next(); 
            System.out.print("New Price: ");
        }
        double price = sc.nextDouble();

        String sql = "UPDATE tbl_tdocument SET t_price = ? WHERE t_id = ?";
        conf.updateResident(sql, price, id); // parameterize the name in the question mark
    }

    public void deleteDoctype() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Document ID to delete: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid Document ID.");
            sc.next();
            System.out.print("Enter Document ID to delete: ");
        }
        int id = sc.nextInt();

        String sql = "DELETE FROM tbl_tdocument WHERE t_id = ?";
        conf.deleteResident(sql, id);
    }
}
