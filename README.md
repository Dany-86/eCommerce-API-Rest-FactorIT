### Bienvenido al repositorio del proyecto e-Commerce-API-Rest-Factor-IT-Challenge

**Consideraciones previas**

- Se utilizo base de datos MySQL.
- Se realizo con la version Java 17.
- Se comparten los procedimientos y los scripts SQL en la carpeta adjuntada en la carpeta raiz del repositorio:

  	/informacion_adicional/sql
- Se comparten las collecciones de pruebas de Postman en:

  	/informacion_adicional/postman_request

- Se comparte documentacion del proyecto generada con Postman:

  [Postman documentation link](https://documenter.getpostman.com/view/22306198/2s8YzWSLkt "Postman documentation")

#### Descripcion

e-Commerce-API-Rest-Factor-IT-Challenge es una App que se encarga de brindar servicios al estilo de un e-commerce, gestionando el manejo de productos, clientes, carritos y compras.

#### Procedimientos

Como esta especificado en el archivo de la carpeta */sql* junto a sus scripts, los pasos a seguir serian:

- Crear una base de datos llamada *ecommercedb*
- Chequear el usuario y contraseña de la base de datos local, en la aplicacion esta seteado el usuario bajo el nombre "*root*" y sin contraseña en el *application.properties*.
- Ejecutar la aplicacion para que se conecte a la base de datos y se generen las tablas.
- Ejecutar los scripts SQL desde un cliente de base de datos para poblar las tablas y poder utilizar sus datos.

##### Consideraciones posteriores

Por falta de tiempo quedo aplicado a medias el patron DTO. Quedaron por realizar un grupo de validaciones adicionales. En algunas clases como *CartServiceImpl* se podrian aplicar una mejor reutilizacion de codigo.

#### Agradecimientos

Desde ya quiero agradercer a Factor-IT y a Kickoff por el interes, la amabilidad y la oportunidad de realizar este challenge.