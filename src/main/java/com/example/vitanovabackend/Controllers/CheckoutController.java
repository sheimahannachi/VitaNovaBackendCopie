package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Payload.PaymentPayload;
import com.example.vitanovabackend.Service.ICheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.Stripe;

import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

ICheckoutService iCheckoutService;
    @PostMapping("/create-checkout-session")
    public String createCheckoutSession() throws StripeException {
        // Initialize Stripe API with your secret key
        Stripe.apiKey = "sk_test_51OGMOXL0ywzjvxffB2ayVrBO79z8m1FZbjjf7GslABbmtvQDHwiuWnh9TVN9O6NqsOX0lejum8e2WHPwEgoH95nD00xZGMKnwR";

        // Create a map to hold the parameters for creating a checkout session
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", new String[]{"card"});
        params.put("line_items", new Object[]{
                new HashMap<String, Object>(){{
                    put("price_data", new HashMap<String, Object>(){{
                        put("currency", "usd");
                        put("product_data", new HashMap<String, Object>(){{
                            put("name", "T-shirt");
                        }});
                        put("unit_amount", 2000);
                    }});
                    put("quantity", 1);
                }}
        });
        params.put("mode", "payment");
        params.put("success_url", "https://example.com/success");
        params.put("cancel_url", "https://example.com/cancel");

        // Create a checkout session using StripeService
        Session session = iCheckoutService.createCheckoutSession(params);

        // Return the session ID to the client
        return session.getId();
    }

    // Replace with your Stripe secret key
    private static final String STRIPE_SECRET_KEY = "sk_test_51OGMOXL0ywzjvxffB2ayVrBO79z8m1FZbjjf7GslABbmtvQDHwiuWnh9TVN9O6NqsOX0lejum8e2WHPwEgoH95nD00xZGMKnwR";

    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentPayload payload) {
        Stripe.apiKey = STRIPE_SECRET_KEY;
        System.out.println("token : "+payload.getStripeToken());
        System.out.println("amount : "+payload.getAmount());
        System.out.println("cardNumber : " +payload.getCardNumber());
        System.out.println("expdate: "+payload.getExpDate());
        System.out.println("name: "+payload.getName());
        System.out.println("cvc: "+payload.getCvc());



        // Create a map to hold the payment details
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", payload.getAmount()); // Amount in cents
        chargeParams.put("currency", "usd");
        chargeParams.put("source", payload.getStripeToken()); // Stripe token obtained from frontend
        chargeParams.put("description", "Premium for " + payload.getName());

        try {
            // Create a charge using the Stripe API
            Charge charge = Charge.create(chargeParams);

            // Payment successful, return success response
            System.out.println("works");
            return ResponseEntity.ok().build();
        } catch (StripeException e) {
            // Payment failed, return error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment: " + e.getMessage());
        }
    }
}
