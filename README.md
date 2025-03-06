# Dauntless

## Build

```shell
./gradlew clean shadowJar
./gradlew generateOpenApiDocs
```

## Deploy

```shell
sam deploy --guided # at the initial deploy
sam deploy --stack-name dauntless
```

## Local Test

run command below after build success

```shell
sam local invoke DauntlessLambda -e input.json -t template.yaml
```
