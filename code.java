import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
public class TextEncryptionController {

    @PostMapping("/encrypt")
    public String encryptText(@RequestParam String text, @RequestParam String key) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            String base64EncryptedText = Base64.getEncoder().encodeToString(encryptedText);

            return base64EncryptedText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while encrypting text";
