FROM hseeberger/scala-sbt

COPY project /root/project/ws-chat/project
COPY build.sbt /root/project/ws-chat/build.sbt
WORKDIR /root/project/ws-chat
RUN sbt clean
RUN sbt reload update
COPY . /root/project/ws-chat
WORKDIR /root/project/ws-chat
RUN sbt clean
RUN sbt compile

EXPOSE 9000
CMD cd /root/project/ws-chat && sbt `echo $CMD`
