This guide was used as a basis: https://spring.io/guides/gs/vault-config/


Vault is setup in dev mode (http) using vault command

```$xslt
vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"
```

The username and password were added to vault using:

```$xslt
vault kv put secret/gs-vault-config example.username=demouser example.password=demopassword
```



Next Step:
Make the secret expire after 1 minute - will spring auto load the new value


