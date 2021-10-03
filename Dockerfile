FROM mysql:5.7

WORKDIR /

COPY db/init.sql /docker-entrypoint-initdb.d/