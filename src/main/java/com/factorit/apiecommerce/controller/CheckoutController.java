package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Item;
import com.factorit.apiecommerce.service.CartService;
import com.factorit.apiecommerce.service.ClientService;
import com.factorit.apiecommerce.service.ProductService;
import com.factorit.apiecommerce.service.PurchaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CheckoutController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PurchaseService purchaseService;

    private static final Logger LOG = LogManager.getLogger(CheckoutController.class);

    // ------------------------------------------ CONSIGNA -------------------------------------------------

    //REQUERIMIENTOS

    //>>Existen dos tipos de carritos, común y especial. Este hecho se determinará con un flag sobre el
    //>>servicio de creación del carrito (isSpecial:true) además de acompañarse la fecha de creación del
    //>>mismo.
    //>>El cliente puede realizar varias compras en el mismo día.
    //>>No es necesario desarrollar ningún tipo de ABM de los productos, ni de clientes. Los productos
    //>>enviados al agregarse al carrito se tomarán como “válidos” y el precio indicado será el que se tome
    //>>como válido (además no se tiene en cuenta stocks de los mismos, así que no hace falta que realices
    //>>control de este punto).
    //>>Los productos deberán tener precios mayores a $100.
    //Para calcular el valor del carrito se debe tener en cuenta:
    // Si se compran más de 3 productos se realizará un descuento de $100 para carritos comunes
    //y de $150 para carritos especiales.
    // Si el cliente en un determinado mes calendario, realizó compras por más de $5.000, pasa a
    //ser considerado VIP y por lo tanto, en su próxima compra, se le aplicará un “descuento
    //especial” de $500 pesos, solo si la compra supera los $2000 (acumulable con el descuento
    //del punto 1).
    // Si el cliente “compra” 4 productos iguales (quantity:4), el sistema aplicará un descuento extra,
    //realizando la promoción 4x3 en dicho producto.
    //Observación: Sería bueno que en la determinación de si un cliente es vip o no, se consuma el
    //servicio de “compras realizadas” para poder iterar y determinar dicha situación (obteniendo el “mes”
    //para el cálculo sobre la fecha de finalización del carrito).

    // CRITERIOS DE EVALUACION
    //A la hora de evaluar las pruebas tendremos en cuenta:
    // Cumplimiento del Objetivo.
    // Pruebas unitarias y/o funcionales
    // Buenas prácticas de desarrollo
    // Adaptabilidad del código.
    // Patrones de Diseño.
    //CONSIDERACIONES
    // Dejamos a libre criterio las definiciones de estructuras a utilizar en cada servicio (Json), pero
    //las mismas deben ser compartidas para poder realizar el consumo correspondiente.
    // En el caso de no poder implementar alguna funcionalidad, no dejes de hacer lo que puedas!
    //Si existe el servicio, pero no filtra, al menos lo podemos probar!
    // Tecnologías: Java 7+ / Spring-SpringBoot / JPA-Hibernate.
    // Exponer servicios REST (agregar proyecto postman. Usar Swagger/OpenAPI).
    // Tests unitarios JUnit/Mockito.
    // Scripts SQL.
    // Se valorará la implementación de algún caso y/o método de testing unitario.
    // Si no conoces algunas de las tecnologías indicadas, podrás reemplazarla indicando por cual
    //y realizando la justificación del reemplazo.
    // Deberás enviar el código mediante algún repositorio GIT.
    // Se permiten agregados o extras a los requerimientos detallados que sumen al desafío
    //propuesto en el tiempo solicitado. Todo esfuerzo se valorará extra!

    // ------------------------------------- MANEJO DE CARRITOS ---------------------------------------------

    //>> Devuelve una lista de todos los clientes
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Cart>> getCarts() {
        return new ResponseEntity<List<Cart>>(this.cartService.getAllClients(), HttpStatus.OK);
    }

    //>> - Consultar el estado de un carrito. Esta acción devolverá el total de productos que
    //contiene
    // recibe dni
    // busca el carrito asociado a ese dni
    // devuelve los productos de ese carrito
    @GetMapping("/{dni}")
    @ResponseBody
    public ResponseEntity<List<Item>> getCart(@PathVariable Integer dni) {
        return new ResponseEntity<List<Item>>(this.cartService.getCartItemsByClient(dni), HttpStatus.OK);
    }

    //>>- Crear un carrito.
    // Recibe dni
    // Crea un Carrito correspondiente a ese dni
    @PostMapping("/{dni}")
    @ResponseBody
    public ResponseEntity<Object> postCart(@PathVariable Integer dni) {
        this.cartService.createCart(dni);
        return new ResponseEntity("Carrito Creado", HttpStatus.CREATED);
    }

    //>> - Eliminar un carrito.
    // recibe un id de carrito
    // elimina el carrito correspondiente al id
    @DeleteMapping("/eliminar-carrito/{dni}")
    @ResponseBody
    public ResponseEntity deleteCart(@PathVariable Integer dni) {
        //System.out.println("En controller DNI: " + dni);
        this.cartService.deleteCartByDni(dni);
        return new ResponseEntity("carrito borrado", HttpStatus.OK);
    }

    // - Finalización de un carrito por compra. Esta acción cerrará el carrito, dando el valor
    //final del mismo (con promociones aplicadas si correspondiesen).
    // recibe el id del carrito
    // aplica las promociones correspondientes para calcular el total
    // crea una nueva compra con los datos del carrito
    // elimina el carrito correspondiente
    // devuelve el id de compra de la nueva compra generada
    @PostMapping("/terminar/{dni}")
    @ResponseBody
    public ResponseEntity closeCart(@PathVariable Integer dni) {
        return new ResponseEntity("", HttpStatus.OK);
    }

    // --------------------------------- MANEJO DE PRODUCTOS DE UN CARRITO -------------------------------------

    //>> - Agregar productos de un carrito.
    // recibe un dni correspondiente al carrito por url
    // recibe un numero de producto por url
    // busca el carrito por id
    // agreag busca un producto por id
    // agrega el producto a la lista del carrito
    @PostMapping("/{dni}/agregar-producto/{id_product}")
    @ResponseBody
    public ResponseEntity postProductToCart(@PathVariable Integer dni, @PathVariable("id_product") Integer idProduct) {
        this.cartService.addProductToCart(dni, idProduct);
        return new ResponseEntity("", HttpStatus.OK);
    }

     //>> - Eliminar productos de un carrito.
    //  recibe un numero de carrito por url
    //  recibe un numero de producto por url
    //   busca el carrito por id
    // elimina el producto por id
    @DeleteMapping("/{dni}/eliminar-producto/{idProduct}")
    @ResponseBody
    public ResponseEntity deleteProductFromCart(@PathVariable Integer dni, @PathVariable Integer idProduct) {
        this.cartService.dropProductFromCart(dni, idProduct);
        return new ResponseEntity(HttpStatus.OK);
    }

}
