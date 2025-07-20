Run db locally
```
docker run --name tts-db \
-e POSTGRES_USER=tts-user \
-e POSTGRES_PASSWORD=tts-pass \
-e POSTGRES_DB=tts-db \
-p 5432:5432 -d postgres
```