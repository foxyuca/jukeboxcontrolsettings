# jukeboxcontrolsettings

docker image build -t jukebox-control-settings .
docker container run -p 8080:8080 jukebox-control-settings
docker ps
http://localhost:8080/actuator/health
docker stop [OPTIONS] CONTAINER [CONTAINER...]


docker-compose build
docker-compose up

http://localhost:8080/swagger-ui.html
