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

#docker network create monitoring
docker network create app-net
docker compose up jaeger prometheus grafana -d

kafka:
http://localhost:9021