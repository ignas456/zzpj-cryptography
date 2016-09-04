# zzpj-cryptography
[![Build Status](https://travis-ci.org/ignas456/zzpj-cryptography.svg?branch=master)](https://travis-ci.org/ignas456/zzpj-cryptography)

## ZZPJCryptography

This is a spring web project which allows to encrypt/decrypt files and texts which works online. It uses Spring Boot and AngularJS as frontend framework.


## Motivation

 We created this application as a part of a task for one of our University's subject.
 

## Installation

If you want to run our project you just need to download it from Github and have Maven installed. You can use any IDE to launch it becuase we use Spring Boot, so you don't have to worry about uploading war on your server - Spring Boot will use it's own embedded server. If you prefer to use command line than you will have to use commands listed below :
1. mvn clean install
2. mvn spring-boot:run

After running those commands, our application is running on embedded spring boot server and you can access it by accessing _localhost:8080/zzpjcryptography_ url in the browser.


## API Reference

Our backend part of application exposes four REST endpoints which provide data consumed by AngularJS part.
These endpoints are as follows :
| URL                   | Http Method | Input type | Input fields | Example input                                 | Response Status                                                           | Response Type |
|-----------------------|-------------|------------|--------------|-----------------------------------------------|---------------------------------------------------------------------------|---------------|
| /api/des/encrypt/text | POST        | JSON       | key , text   | {"key" : "ABCD1234, "text":"some"}            | 200 - text encrypted and returned, 400 - invalid key                      | text/plain    |
| /api/des/decrypt/text | POST        | JSON       | key , text   | {"key" : "ABCD1234, "text":"some"}            | 200 - text decrypted and returned 400 - invalid key or text not in base64 | text/plain    |
| /api/des/encrypt/file | POST        | FormData   | key , file   | --------------------------------------------- | 200 - file encrypted and returned 400 - invalid key                       | text/plain    |
| /api/des/decrypt/file | POST        | FormData   | key , file   | --------------------------------------------- | 200 - file encrypted and returned 400 - invalid key                       | text/plain    |

## Tests

To run tests just use your favourite IDE or go to root folder of the project and use _mvn test_ command.

## License

MIT License
Copyright (c) [2016] [Michał Krzywański, Przemysław Guzek, Michał Ignaczewski, Oskar Bogacz]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
