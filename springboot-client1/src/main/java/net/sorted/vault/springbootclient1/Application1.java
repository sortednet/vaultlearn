package net.sorted.vault.springbootclient1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.vault.core.VaultSysOperations;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultMount;
import org.springframework.vault.support.VaultResponse;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application1 implements CommandLineRunner {

    @Autowired
    private VaultTemplate vaultTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application1.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        int version = 2;
        if (strings.length > 0) {
            String v = strings[0];
            try {
                version = Integer.parseInt(v);
            } catch (NumberFormatException e) {
                System.out.println("Version arg must be an integer - '" + v + "' is not an int");
                System.exit(-1);
            }
        }

        String secretPath = (version == 1 ) ? "secret/github" : "secret/data/github";

        // You usually would not print a secret to stdout
		VaultResponse response = vaultTemplate.read(secretPath);

        Map<String, Object> data;
        Object githubKey;
        if (version == 1) {
            data = response.getData();
        } else {
            data = (Map<String, Object>) response.getData().get("data");
        }

        githubKey = data.get("github.oauth2.key");

		System.out.println("Value of github.oauth2.key");
        System.out.println("-------------------------------");
        System.out.println(githubKey);
        System.out.println("-------------------------------");
        System.out.println();


        // Let's encrypt some data using the Transit backend.
        VaultTransitOperations transitOperations = vaultTemplate.opsForTransit();

        // We need to setup transit first (assuming you didn't set up it yet).
        VaultSysOperations sysOperations = vaultTemplate.opsForSys();

        if (!sysOperations.getMounts().containsKey("transit/")) {

            sysOperations.mount("transit", VaultMount.create("transit"));

            transitOperations.createKey("foo-key");
        }

        // Encrypt a plain-text value
        String ciphertext = transitOperations.encrypt("foo-key", "Secure message");

        System.out.println("Encrypted value");
        System.out.println("-------------------------------");
        System.out.println(ciphertext);
        System.out.println("-------------------------------");
        System.out.println();

        // Decrypt

        String plaintext = transitOperations.decrypt("foo-key", ciphertext);

        System.out.println("Decrypted value");
        System.out.println("-------------------------------");
        System.out.println(plaintext);
        System.out.println("-------------------------------");
        System.out.println();


        // NB kv v2 has 'data' in the path. Need to align to this using a wrapping 'data' map with just the data key.
        Map<String, Object> dw;
        if (version == 1) {
            dw = data;
        } else {
            dw = new HashMap<>();
            dw.put("data", data);
        }



        data.put("bar-key", ciphertext);
        vaultTemplate.write(secretPath, dw);


    }
}
