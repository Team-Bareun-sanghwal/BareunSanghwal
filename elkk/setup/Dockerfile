ARG ELASTIC_VERSION

FROM docker.elastic.co/elasticsearch/elasticsearch:${ELASTIC_VERSION}

# Dockerfile과 같은 디렉토리에 entrypoint.sh가 있다고 가정
COPY entrypoint.sh /entrypoint.sh

# 스크립트가 실행 가능하도록 설정
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
