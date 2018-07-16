import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        rsaDemo();

    }

    public static void rsaDemo() {
        RSA encryption = new RSA();
        Scanner s = new Scanner(System.in);
        long val = 1;
        System.out.println("Tim's RSA Demo, Enter 0 to Quit");
        while (val > 0) {
            try {
                Thread.sleep(100);
                System.out.print("Enter Number : ");
                val = Integer.parseInt(s.nextLine());
                System.out.println(val);
                val = encryption.encrypt(val);
                System.out.println(val);
                val = encryption.decrypt(val);
                System.out.println(val);
            } catch (NumberFormatException e) {
                System.err.println("Number is invalid");
            } catch (InterruptedException e) {
                System.err.println("Shouldn't be seeing this");
            }
        }
    }
}



