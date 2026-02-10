import javax.crypto.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class AESEncryptionTool {

    static Key key;

    public static void main(String[] args) {

        int menuOption = 0;
        Scanner scan = new Scanner(System.in);
        while(menuOption != 3) {

            System.out.println("""
                    
                    -----Encrypt/Decrypt-----
                    1). Encrypt file
                    2). Decrypt file
                    3). Exit
                    """);

            menuOption = scan.nextInt();
            switch (menuOption) {
                case 1 : encryptFile(); break;
                case 2 : decryptFile(); break;
                case 3 : break;
            }

        }
    }

    public static void encryptFile() {

        byte[] fileBytes;

        try {
            fileBytes = Files.readAllBytes(Paths.get("Test.txt"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");

            generator.init(192);
            key = generator.generateKey();

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedFileBytes = cipher.doFinal(fileBytes);

            Path path = Paths.get("Test.txt");
            Files.write(path, encryptedFileBytes);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | IOException e) {
            throw new RuntimeException(e);
        }


    }



    public static void decryptFile() {

        byte[] fileBytes;

        try {
            fileBytes = Files.readAllBytes(Paths.get("Test.txt"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");

            generator.init(192);
            //Key key = generator.generateKey();

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] encryptedFileBytes = cipher.doFinal(fileBytes);

            Path path = Paths.get("Test.txt");
            Files.write(path, encryptedFileBytes);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
