FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ENV GRADLE_USER_HOME /usr/bin/app/.gradle
WORKDIR /usr/bin/app

# Set the default entrypoint
ENTRYPOINT ["gradle"]

# Set the default command
CMD ["assembly", "check"]