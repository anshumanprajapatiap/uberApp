# uberApp


Install Homebrew on Mac
```shell
    brew install postgis
```
```shell
    brew install postgresql@14
    brew services start postgresql@14
    brew services stop postgresql@14
```
- Start Postgres server
```shell
    brew services start postgresql@14
    brew services stop postgresql@14
```
```shell
    createdb uberdb
```
```shell
    psql -d uberdb
```
```shell
   CREATE EXTENSION postgis;
```
```shell
   SELECT PostGIS_VERSION();
```
