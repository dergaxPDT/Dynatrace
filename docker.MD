To run docker paste that too terminal:
1. docker build --build-arg JAR_FILE=build/libs/*.jar -t myorg/myapp .
2. docker run -p 9000:9000 myorg/myapp --server.port=9000
After that must show swagger ur: http://localhost:9000/swagger-ui.html :) 
