# Alfa-bank тестовое задание

## Что делает ?

Это сервис, который отображает гиф в зависимости от курса валют.  
Если курс сегодня больше вчерашнего, рандомная гифка берется отсюда:  
https://giphy.com/search/rich, иначе отсюда: https://giphy.com/search/broke

**Доступный API:**
1) https://docs.openexchangerates.org/ _(REST API курсов валют)_
2) https://developers.giphy.com/docs/api#quick-start-guide _(REST API гифок )_

---

## Как запустить ?

**Docker Hub**

Чтобы не заставлять вас ждать билда, я залил образ приложения на docker hub.  
Для его запуска необходимо:
1) Скачать докер-образ:  
   `docker pull vml1337j/gif-service`
2) Запустить докер-образ при помощи команды:
```
docker run \
-e EXCHANGE_API_KEY= \
-e EXCHANGE_API_BASE= \
-e GIPHY_API_KEY= \
-p 8080:8080 \
vml1337j/gif-service
```  
- EXCHANGE_API_KEY - апи-ключ для openexchangerates.org/api
- EXCHANGE_API_BASE - валюта относительно которой смотрится курс  
_(опционально, по умолчанию USD)_
- GIPHY_API_KEY - апи-ключ для api.giphy.com/v1  
_(апи-ключи следует написать сразу после равно, между ключем и "\\" должен  
стоять пробел, например: `EXCHANGE_API_KEY=12345 \`)_
- Порт можно указать удобный для вас, например: 8989:8080

3) Перейти на ендпоинт (в примере RUB, доступные валюты 
[тут](https://docs.openexchangerates.org/docs/supported-currencies#list-of-supported-symbols-and-currency-names)):  
`http://localhost:8080/api/v1/currencies/RUB/rate`   
_(Нужно указать свой порт)_

---

**Весь процесс сборки**

Чтобы самостоятельно собрать и запустить приложение нужно:

1) Склонировать репозиторий, удобным для вас способом, например через консоль:  
`git clone https://github.com/vml1337j/gif-service.git`

2) Перейти в папку с проектом, например:  
`cd gif-service`

3) Собрать проект при помощь ./gradlew:  
`./gradlew clean bootJar`

4) Запустить сборку образа:  
`docker build -f Dockerfile -t gif-service .`

5) После сборки запустить докер-образ при помощи команды:  
```
docker run \
-e EXCHANGE_API_KEY= \
-e EXCHANGE_API_BASE= \
-e GIPHY_API_KEY= \
-p 8080:8080 \
gif-service
```  

- EXCHANGE_API_KEY - апи-ключ для openexchangerates.org/api
- EXCHANGE_API_BASE - валюта относительно которой смотрится курс  
_(опционально, по умолчанию USD)_
- GIPHY_API_KEY - апи-ключ для api.giphy.com/v1   
  _(апи-ключи следует написать сразу после равно, между ключем и "\\" должен  
  стоять пробел, например: `EXCHANGE_API_KEY=12345 \`)_
- Порт можно указать удобный для вас, например: 8989:8080

6)Перейти на ендпоинт (в примере RUB, доступные валюты [
тут](https://docs.openexchangerates.org/docs/supported-currencies#list-of-supported-symbols-and-currency-names)):
`http://localhost:8080/api/v1/currencies/RUB/rate`  
_(Нужно указать свой порт)_

  _(В 3 пункте: предварительно собираем приложение, чтобы билд докер-образа  
был не таким долгим)_

---