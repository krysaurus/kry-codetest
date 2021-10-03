FROM node:16-alpine

RUN npm install -g nodemon
WORKDIR /usr/src/app

COPY package.json /usr/src/app/

RUN npm install && mv /usr/src/app/node_modules /node_modules

COPY app/index.js /usr/src/app
COPY app /usr/src/app
EXPOSE 8080
CMD [ "nodemon", "index.js" ]