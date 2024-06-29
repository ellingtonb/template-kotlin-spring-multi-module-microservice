.PHONY: docker-build docker-image-rm docker-run

docker-build:
	@EXPORT_VARS_FILES=false \
	SPRING_PROFILES_ACTIVE=dev-local \
	JAVA_OPTS="--add-opens java.base/java.nio=ALL-UNNAMED -XX:InitialRAMPercentage=40.0 -XX:MinRAMPercentage=40.0 -XX:MaxRAMPercentage=90.0" \
	docker build . -t template-kotlin-spring-multi-module-microservice;

docker-image-rm:
	@docker image rm template-kotlin-spring-multi-module-microservice;

docker-run:
	@docker run --rm -it \
		--name template-kotlin-spring-multi-module-microservice \
		-e APP_PORT="8076" \
		-e MONITORING_PORT="8077" \
		-e SPRING_PROFILES_ACTIVE="dev-local" \
		--network=host \
		template-kotlin-spring-multi-module-microservice:latest;
