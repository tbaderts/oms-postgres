swagger:
http://localhost:8090/swagger-ui/index.html#

pgadmin:
http://localhost:5050

jaeger:
http://localhost:16686

grafana:
http://localhost:3000
admin/secret

prometheus:
http://localhost:9090
http://localhost:8090/actuator/metrics

local:
export DB_URL=jdbc:postgresql://localhost:5432/oms
export DB_USER=postgres
export DB_PASSWORD=changeme
export KAFKA_BROKERS=localhost:9092
export ORDER_TOPIC=oms_orders
export CONSUMER_GROUP=oms_consumer_group
export REGISTRY_URL=http://localhost:8081
export TX_ID_PREFIX=tx-
export DEAD_LETTER_TOPIC=oms_dlq
export TRACING_URL=http://localhost:4317
export SERVER_PORT=8090

docker:
export DB_URL=jdbc:postgresql://postgres:5432/oms
export DB_USER=postgres
export DB_PASSWORD=changeme
export KAFKA_BROKERS=broker:29092
export ORDER_TOPIC=oms_orders
export CONSUMER_GROUP=oms_consumer_group
export REGISTRY_URL=http://schema-registry:8081
export TX_ID_PREFIX=tx-
export DEAD_LETTER_TOPIC=oms_dlq
export TRACING_URL=http://jaeger:4317
export SERVER_PORT=8090