FROM node:18-alpine As deps

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY ["package.json", "yarn.lock", "tsconfig.json", "next.config.mjs", "./"]
RUN yarn install

FROM node:18-alpine As builder

WORKDIR /usr/app
COPY --from=deps /usr/app/package.json ./package.json
COPY --from=deps /usr/app/yarn.lock ./yarn.lock
COPY --from=deps /usr/app/tsconfig.json ./tsconfig.json
COPY --from=deps /usr/app/next.config.mjs ./next.config.mjs
COPY --from=deps /usr/app/node_modules ./node_modules
COPY ./ ./

RUN yarn build

FROM node:18-alpine As runner

WORKDIR /usr/app
COPY --from=builder /usr/app/package.json ./package.json
COPY --from=builder /usr/app/yarn.lock ./yarn.lock
COPY --from=builder /usr/app/tsconfig.json ./tsconfig.json
COPY --from=builder /usr/app/next.config.mjs ./next.config.mjs
COPY --from=builder /usr/app/node_modules ./node_modules
COPY --from=builder /usr/app/public ./public
COPY --from=builder /usr/app/.next ./.next

EXPOSE 3000

CMD ["yarn", "start"]
