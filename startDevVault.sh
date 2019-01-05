#!/bin/bash -eux

vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000" 

if [[ $# -eq 1 ]]; then
  $KV_VERSION=$1
  echo "Switch to kv version ${KV_VERSION}"
  vault secrets disable secret
  vault secrets enable -version=${KV_VERSION} -path=secret -description='local secrets' kv
fi
