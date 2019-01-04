based on : https://spring.io/guides/gs/accessing-vault/

Working but needed to change the path in the code to secret/*data*/github

This is due to using the KV version 2 engine. This is default in dev mode but prod mode requires an update

see https://www.vaultproject.io/docs/secrets/kv/kv-v2.html



Vault is setup in dev mode (http) using vault command

```$xslt
vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"
```

The added to vault using:

```$xslt
vault kv put secret/github github.oauth2.key=foobar
```

vault write secret/github github.oauth2.key=foobar