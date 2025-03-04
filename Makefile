.PHONY: build-DauntlessLambda

build-DauntlessLambda:
	./gradlew clean shadowJar
	mkdir -p $(ARTIFACTS_DIR)
	cp build/libs/*.jar $(ARTIFACTS_DIR)
