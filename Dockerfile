FROM maven:latest AS MAVEN_TOOL_CHAIN
WORKDIR /body/
RUN curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash


 
#RUN echo "Host github.com\m-ostrov\pelmen\tStrictHostKeyChecking no\n" >> /root/.ssh/config

RUN apt-get install git
RUN git clone https://github.com/m-ostrov/body.git

RUN ls body
RUN mvn -f body/pom.xml org.springframework.boot:spring-boot-maven-plugin:run
#ENTRYPOINT ["/bin/sh","-C","cd","body"]

 


 