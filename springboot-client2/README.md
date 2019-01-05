Based on https://cloud.spring.io/spring-cloud-vault/

NOT WORKING

Could not resolve placeholder 'config.name' in value "${config.name}"

Added data with:

```$xslt
vault kv put secret/springboot-client2 config.name=doug
```



```$xslt
$ vault kv get secret/springboot-client2
====== Metadata ======
Key              Value
---              -----
created_time     2019-01-02T06:46:38.517354Z
deletion_time    n/a
destroyed        false
version          5

======= Data =======
Key            Value
---            -----
config.name    doug
```
