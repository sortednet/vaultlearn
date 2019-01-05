based on : https://spring.io/guides/gs/accessing-vault/


This contains pretty much all that is needed for the SAPM sync app (encrypts and writes to vault). Probably dont even ned the encryption.

Will need to also work out how to add roles programatically.

Working but needed to change the path in the code to secret/*data*/github

This is due to using the KV version 2 engine. This is default in dev mode but prod mode requires an update

see https://www.vaultproject.io/docs/secrets/kv/kv-v2.html


Main diff in code is that when reading, the path to the response object contains the data part (secret/data/xxxxx) and 
it returns a map that contains the key 'data' which contains the actual data. When writing, a map with the key 'data' 
pointing to the actual data must be used. 



** We must decide if we use the v1 (unversioned) or v2 (versioned) engine before we use vault **



Vault is setup in dev mode (http) using vault command

```$xslt
vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"
```

The added to vault using:

```$xslt
vault kv put secret/github github.oauth2.key=foobar
```

vault write secret/github github.oauth2.key=foobar