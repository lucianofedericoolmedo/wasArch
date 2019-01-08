# ESTRUCTURA

ESTE ES UN PROYECTO MAVEN CON ESTRUCTURA MULTIPROYECTO:

Proyecto padre (pom.xml suelto en el root del proyecto)
tiene dependencias comunes y describe los módulos.

-- --- 1 carpeta por subproyecto
-- --- 1 módulo de api (pom.xml)
-- --- 1 módulo de frontend(pom.xml) sólo empaqueta el front. ( en este se usa un plugin de maven que invoca al angular-cli) 
	https://github.com/eirslett/frontend-maven-plugin
-- --- 1 módulo que arme el .ear.(pom.xml) sin code solo ensambla todo.
-- --- 1 carpeta del front. ( tiene los paquetes de node y todo lo de angular, se usa con angular-cli)

Visto de otro modo: 

-- PADRE

-- --- HIJO BACKEND (con POM) → API → .WAR => Donde están los endpoint de la API REST (nada de frontend!!)
-- --- HIJO EAR (me genera EAR, tmb tiene POM) → Empaquetador → .EAR => Que lo que hace poner todos los JAR y WAR dentro de un solo paquete.
-- --- HIJO FRONTEND (con POM) → Con el plugin frontend-maven-plugin → 1) Baja Node.js y 1’) Siempre ejecuta npm install 2) Invoca Build usando angular-cli (ng build --prod) que está configurado en el proyecto ../frontend → 3) Maven toma lo que esté en la carpeta ../frontend/dist y arma un WAR con ese contenido.
-- --- FRONTEND 

Tenemos tres partes para poder observar para poder levantar el entorno.

## Levantando el frontend de forma independiente (util para desarrollar, requiere que el backend este levantado, esto puede ser de forma aislada o todo el proyecto)

npm start

(levanta en puerto 4200)

Observar README.md en example-front (que empaqueta frontend) y frontend

## Levantando backend aislado

Ir a example-api y correr 

mvn install

(Puede hacerse uso de un servidor embebido que se puede correr con mvn liberty:run-server o copiando el war generado en la carpeta del servidor correspondiente).

Puede configurar los puertos en el archivo server.xml
-- ---

## Levantar entorno completo

Y para levantar el proyecto en websphere (u liberty)

### Entorno linux

## Correr archivo (creado en base a otro) build-dev.sh (hay un .example, el cual que copiar y modificar de acuerdo a entorno), en entornos linux

sh build-dev.sh 

(En este caso tiene los mismos archivos de configuracion que el proyecto api, con lo cual si quiere otros debe modificar el script y pasarle otro server.xml, o sea "Levantar entorno completo" y "Levantando backend aislado" como backend tienen muchas similitudes).

En entornos windows debe hacerse algo parecido

