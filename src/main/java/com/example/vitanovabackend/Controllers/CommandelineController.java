package com.example.vitanovabackend.Controllers;

import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/Order")
@MultipartConfig
@CrossOrigin
public class CommandelineController {



    /*OrderIService orderIService;
    @PostMapping("addProductToOrder/{idOrder}/{idPr}")
    public void addProductToOrder(@PathVariable Long idOrder,@PathVariable Long idPr){
        orderIService.addProductToOrder(idOrder,idPr);
    }
    @PostMapping("addOrder")
    public Order addOrder(@RequestBody Order order){
        return orderIService.addOrder(order);
    }
    @GetMapping("/{idOrder}")
    public Order getOrderById(@PathVariable Long idOrder){

        return orderIService.getOrderById(idOrder);
    }
    @PutMapping("/{idOrder}")
    public Order updateOrder(Order order){
        return orderIService.updateOrder(order);
    }
    @PutMapping("/archive/{idOrder}")
    public ResponseEntity<String> archiverOrder(Long idOrder){
        return  orderIService.archiverOrder(idOrder);
    }
    @GetMapping("getAllOrders")
    public List<Order> getAllOrders(){
        return orderIService.getAllOrders();
    }*/
}
