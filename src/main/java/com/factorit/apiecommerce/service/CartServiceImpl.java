package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.dto.*;
import com.factorit.apiecommerce.model.*;
import com.factorit.apiecommerce.repository.CartRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private PurchaseService purchaseService;

    private static final Logger LOG = LogManager.getLogger(CartServiceImpl.class);

    @Override
    public List<Cart> getAllCarts() {
        return this.cartRepository.findAll(Sort.by("id").ascending());
    }

    public ResponseDTO createCart(Integer dni) {
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);
        Client client = this.clientService.getClientByDni(dni);
        if(cart==null) {
            Cart newCart = new Cart(false, LocalDate.now(), client, null);
            Cart savedCart = this.cartRepository.save(newCart);
            return new ResponseDTO(savedCart.getId(), "El carrito fue creado exitosamente");
        } else {
            return new ResponseDTO(cart.getId(), "Ya existe un carrito");
        }
    }

    @Override
    public List<Item> getCartItemsByDni(Integer dni) {
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);
        if(cart==null) {
            List<Item> emptyList = new ArrayList<>();
            return emptyList;
        } else {
            return cart.getItems();
        }
    }

    @Override
    public ResponseDTO deleteCartByDni(Integer dni) {
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);
        if(cart==null) {
            return new ResponseDTO(-1, "No existe ningun carrito para este cliente");
        } else {
            this.itemService.deleteAllByCart(cart);
            this.cartRepository.delete(cart);
            return new ResponseDTO(1, "Carrito borrado exitosamente");
        }
    }

    @Override
    public ResponseDTO addProductToCart(Integer dni, Integer idProduct) {
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);

        if(cart!=null) {
            Product product = this.productService.getProductById(idProduct);
            if(cart.getItems().isEmpty()) {
                Item newItem = new Item(product, 1);
                Item savedItem = this.itemService.saveItem(newItem);
                List<Item> itemList = cart.getItems();
                itemList.add(savedItem);
                cart.setItems(itemList);
                Cart savedCart = this.cartRepository.save(cart);
                return new ResponseDTO(savedCart.getId(), "Producto agregado correctamente al carrito");
            } else {
                boolean wasFound = false;
                List<Item> newItemList = cart.getItems();
                for (Item eachItem : newItemList) {
                    if (product.getId().equals(eachItem.getProduct().getId())) {
                        eachItem.setQuantity(eachItem.getQuantity() + 1);
                        wasFound = true;
                    }
                }
                if(wasFound){
                    cart.setItems(newItemList);
                    Cart savedCart = this.cartRepository.save(cart);
                    return new ResponseDTO(savedCart.getId(), "Producto agregado correctamente al carrito");
                } else { // si no lo encontramos...
                    Item newItem = new Item(product, 1);
                    Item savedItem = this.itemService.saveItem(newItem);
                    newItemList.add(savedItem);
                    cart.setItems(newItemList);
                    Cart savedCart = this.cartRepository.save(cart);
                    return new ResponseDTO(savedCart.getId(),"Producto agregado correctamente al carrito");
                }
            }
        } else {
            return new ResponseDTO(-1, "El carrito no existe. Cree un carrito primero");
        }
    }

    @Override
    public ResponseDTO dropProductFromCart(Integer dni, Integer idProduct) {
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);
        if(cart!=null) {
            if(!cart.getItems().isEmpty()) {
                List<Item> newItemList = cart.getItems();
                Item item = new Item();
                boolean wasFound = false;
                for (Item eachItem : newItemList) {
                    if (eachItem.getProduct().getId().equals(idProduct)) {
                        item = eachItem;
                        wasFound = true;
                    }
                }
                if (wasFound) {
                    newItemList.remove(item);
                    cart.setItems(newItemList);
                    this.cartRepository.save(cart);
                    this.itemService.deleteByItem(item);
                    LOG.info("Producto fue eliminado del carrito correctammente");
                    return new ResponseDTO("Producto fue eliminado del carrito correctammente");
                } else {
                    LOG.info("El producto no se encuntro en el carrito");
                    return new ResponseDTO(idProduct, "El producto no se encuntro en el carrito");
                }
            } else {
                LOG.info("No se encontro el producto. El carrito ya esta vacio");
                return new ResponseDTO(idProduct,"No se encontro el producto. El carrito ya esta vacio");
            }
        } else {
            LOG.info("No se encontro el producto. El carrito no existe. Cree uno");
            return new ResponseDTO(idProduct,"No se encontro el producto. El carrito no existe. Por favor cree uno");
        }
    }

    @Override
    public Purchase closeCart(Integer dni) {
        Client client = this.clientService.getClientByDni(dni);
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);
        if(cart==null) {
            LOG.info("El cliente especificado no contiene un carrito creado");
            return new Purchase(-2,null, null, null);
        } else {
            if (cart.getItems().isEmpty()) {
                LOG.info("El cliente especificado no contiene productos en su carrito");
                return new Purchase(-1,null, null, null);
            } else {
                boolean isSpecial = cart.getIsSpecial();
                Double total = 0.00;
                Double totalWithProm = 0.00;
                int count = 0;
                List<Item> itemList = cart.getItems();
                for (Item eachItem : itemList) {
                    count = count + eachItem.getQuantity();
                    total = total + (eachItem.getProduct().getPrice() * eachItem.getQuantity());
                    if ((eachItem.getQuantity() > 3)) {
                        Integer countProm = eachItem.getQuantity() / 4;
                        Integer promQuatity = eachItem.getQuantity() - countProm;
                        totalWithProm = totalWithProm + (eachItem.getProduct().getPrice() * promQuatity);
                    } else {
                        totalWithProm = totalWithProm + (eachItem.getProduct().getPrice() * eachItem.getQuantity());
                    }
                }
                if (client.getVip() && total > 2000.00) {
                    totalWithProm = totalWithProm - 500.00;
                }
                if(isSpecial && count > 3) {
                    totalWithProm = totalWithProm - 150.00;
                }
                if (!isSpecial && count > 3) {
                    totalWithProm = totalWithProm - 100.00;
                }
                Double t = totalWithProm.doubleValue();
                Purchase savedPurchase = this.purchaseService.savePurchase(new Purchase(LocalDate.now(), t, client));
                this.deleteCartByDni(dni);
                Client vipCheckedClient = this.clientService.checkIfisVip(client);
                this.clientService.saveClient(client);
                return savedPurchase;
            }
        }
    }

    @Override
    public TotalDTO getTotal(Integer dni) {
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);
        if(cart==null) {
            LOG.info("El cliente especificado no contiene un carrito creado");
            return new TotalDTO("El cliente especificado no contiene un carrito creado");
        } else {
            boolean isSpecial = cart.getIsSpecial();
            if (cart.getItems().isEmpty()) {
                LOG.info("El cliente especificado no contiene productos en su carrito");
                return new TotalDTO("El cliente especificado no contiene productos en su carrito");
            } else {
                Double total = 0.00;
                Double totalWithProm = 0.00;
                int count = 0;
                List<Item> itemList = cart.getItems();
                for (Item eachItem : itemList) {
                    count = count + eachItem.getQuantity();
                    total = total + (eachItem.getProduct().getPrice() * eachItem.getQuantity());
                    // Suma los precios aplicando promocion 4x3 si existiera
                    if ((eachItem.getQuantity() > 3)) {
                        Integer countProm = eachItem.getQuantity() / 4;
                        Integer promQuatity = eachItem.getQuantity() - countProm;
                        totalWithProm = totalWithProm + (eachItem.getProduct().getPrice() * promQuatity);
                    } else {
                        totalWithProm = totalWithProm + (eachItem.getProduct().getPrice() * eachItem.getQuantity());
                    }
                }
                //Si es VIP y el monto es superior a 2000.00 (sin aplicar promociones) aplica promocion acumulable descontando 500.00
                if (this.clientService.getClientByDni(dni).getVip() && total > 2000.00) {
                    totalWithProm = totalWithProm - 500.00;
                }
                // Si lleva mas de 3 productos y es carrito especial descuenta 150.00
                if(isSpecial && count > 3) {
                    totalWithProm = totalWithProm - 150.00;
                    return new TotalDTO(totalWithProm, (totalWithProm - total), "Precio total con descuentos aplicados");
                }
                // Si lleva mas de 3 productos y es carrito comun descuenta 100.00
                if (!isSpecial && count > 3) {
                    totalWithProm = totalWithProm - 100.00;
                    return new TotalDTO(totalWithProm, (totalWithProm - total), "Precio total con descuentos aplicados");
                }
                return new TotalDTO(totalWithProm, (totalWithProm - total), "Precio total con descuentos aplicados");
            }
        }
    }


}
