
FROM nginx:1.19.8

COPY --from=generator /output /asyncapi

COPY nginx.conf /etc/nginx/nginx.conf