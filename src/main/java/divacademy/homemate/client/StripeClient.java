package divacademy.homemate.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "stripe-svc",url = "https://api.stripe.com",path = "/v1/payment_intents")
public interface StripeClient {
    @GetMapping
    public ResponseEntity<Object> payment(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestHeader(name = "Payment") String payment,
            @RequestHeader(name = "currency") String currency,
            @RequestHeader(name = "amount") String amount
    );
}
