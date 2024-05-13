FROM node:18-alpine

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY ["package.json", "yarn.lock", "tsconfig.json", "./"]
RUN yarn install 

COPY ./ ./
RUN yarn build

EXPOSE 3000

CMD ["yarn", "start"]
