FROM ubuntu:latest
MAINTAINER omar almasadeh <omarjmasadeh@gmail.com>

# installing jdk and maven
RUN apt-get -y update
RUN apt install -y default-jre
RUN apt install -y default-jdk
RUN apt install -y maven

# instailling git and adding remote repo
RUN apt-get -y install git
RUN git init
RUN git remote add origin https://github.com/OmarAlMasadeh/map-reduce.git
RUN git pull origin master

#compilling the java files
RUN mvn clean package
RUN cd target
RUN java -cp MapReduce-1.0-SnapShot.jar ManagerMain
