import java.util.Scanner;

public class TDHilal {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            QueueTD blue = new QueueTD();
            QueueTD red = new QueueTD();
            
            int panjangLabirin = input.nextInt();
            input.nextLine();
            String[][] labirin = new String[panjangLabirin][panjangLabirin];
    
            String koordinat = input.nextLine();
            String[] koordinatString = koordinat.split("\\s");
            int[] x = new int[koordinatString.length];
            int[] y = new int[koordinatString.length];
            for (int i = 0; i < koordinatString.length; i++) {
                String[] koordinatSplit = koordinatString[i].split(",");
                x[i] = Integer.valueOf(koordinatSplit[0]);
                y[i] = Integer.valueOf(koordinatSplit[1]);
            }
    
            for (int i = 0; i < labirin.length; i++) {
                for (int j = 0; j < labirin[i].length; j++) {
                    labirin[i][j] = "[XX]";
                }
            }
    
            for (int i = 0; i < x.length; i++) {
                labirin[x[i]][y[i]] = "[OO]";
            }
    
            int jumlahPemain = input.nextInt();
            input.nextLine();
    
            for (int i = 0; i < jumlahPemain; i++) {
                String nama = input.next();
                String command = input.nextLine();
                blue.addLast(nama, labirin, command, 1, x[0], y[0], x[x.length-1], y[y.length-1]);
            }
    
            System.out.println("BLUE TEAM ENTERED RED TOWER");
            blue.jelajah();
            blue = blue.sort();
            blue.cetakLabirin();
            blue.cetakNama();
            System.out.println();
            
            panjangLabirin = input.nextInt();
            input.nextLine();
            labirin = new String[panjangLabirin][panjangLabirin];
    
            koordinat = input.nextLine();
            koordinatString = koordinat.split("\\s");
            x = new int[koordinatString.length];
            y = new int[koordinatString.length];
            for (int i = 0; i < koordinatString.length; i++) {
                String[] koordinatSplit = koordinatString[i].split(",");
                x[i] = Integer.valueOf(koordinatSplit[0]);
                y[i] = Integer.valueOf(koordinatSplit[1]);
            }
    
            for (int i = 0; i < labirin.length; i++) {
                for (int j = 0; j < labirin[i].length; j++) {
                    labirin[i][j] = "[XX]";
                }
            }
    
            for (int i = 0; i < x.length; i++) {
                labirin[x[i]][y[i]] = "[OO]";
            }
    
            jumlahPemain = input.nextInt();
            input.nextLine();
    
            for (int i = 0; i < jumlahPemain; i++) {
                String nama = input.next();
                String command = input.nextLine();
                red.addLast(nama, labirin, command, 1, x[0], y[0], x[x.length-1], y[y.length-1]);
            }
    
            System.out.println("RED TEAM ENTERED red TOWER");
            red.jelajah();
            red = red.sort();
            red.cetakLabirin();
            red.cetakNama();
            System.out.println();
    
            if (blue.head.step < red.head.step) {
                System.out.println("BLUE TOWER TEAM IS WINNER");
                System.out.println(blue.head.step + " steps vs " + red.head.step + " steps");
            } else if (blue.head.step > red.head.step) {
                System.out.println("RED TOWER TEAM IS WINNER");
                System.out.println(red.head.step + " steps vs " + blue.head.step + " steps");   
            } else if (blue.head.step == red.head.step) {
                System.out.println("BOTH TEAMS ARE TIED, NO WINNER");
                System.out.println(blue.head.step + " steps vs " + red.head.step + " steps");   
            }
        }
    }
}