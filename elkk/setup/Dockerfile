ARG ELASTIC_VERSION

FROM docker.elastic.co/elasticsearch/elasticsearch:${ELASTIC_VERSION}

RUN ["chmod", "+x", "./entrypoint.sh"]

ENTRYPOINT ["/entrypoint.sh"]
