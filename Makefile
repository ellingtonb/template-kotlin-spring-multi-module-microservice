.PHONY: docker-build docker-image-rm docker-run

docker-build:
	@EXPORT_VARS_FILES=false \
	SPRING_PROFILES_ACTIVE=dev-local \
	SERVICE_NAME=template-kotlin-spring-multi-module-microservice \
	JAVA_OPTS="--add-opens java.base/java.nio=ALL-UNNAMED -XX:InitialRAMPercentage=40.0 -XX:MinRAMPercentage=40.0 -XX:MaxRAMPercentage=90.0" \
	docker build . -t template-kotlin-spring-multi-module-microservice;

docker-image-rm:
	@docker image rm template-kotlin-spring-multi-module-microservice;

docker-run:
	@if [ ! -f "${PWD}/.env.docker" ]; then \
		echo "The .env.docker file is required to load the environment variables!"; \
		exit 1; \
	fi && \
	docker run --rm -it \
		--name template-kotlin-spring-multi-module-microservice \
		--env-file "${PWD}/.env.docker" \
		-p 8076:8076 \
		-p 8077:8077 \
		-e SPRING_PROFILES_ACTIVE="dev-local" \
		-e JAVA_OPTS="--add-opens java.base/java.nio=ALL-UNNAMED -XX:InitialRAMPercentage=40.0 -XX:MinRAMPercentage=40.0 -XX:MaxRAMPercentage=90.0" \
		template-kotlin-spring-multi-module-microservice:latest;
