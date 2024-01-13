package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CinemaController {
    private Cinema cinema = Cinema.getInstance();
    private List<Purchased> purchasedTickets = new ArrayList<>();
    @GetMapping("/seats")
    public Cinema getTickets() {
        return cinema;
    }
    String access = "super_secret";
    @PostMapping("/purchase")
    public ResponseEntity purchaseTicket(@RequestBody Ticket purchase) {
        purchase.setPrice();
        boolean outOfBound = purchase.getRow() > cinema.getRows() || purchase.getColumn() > cinema.getColumns() || purchase.getRow() < 0 || purchase.getColumn() < 0;
        boolean isUnavailable = purchasedTickets.stream().anyMatch(s -> s.getTicket().getRow() == purchase.getRow() && s.getTicket().getColumn() == purchase.getColumn());
        if(outOfBound)
            return ResponseEntity.badRequest()
                    .body(new Error("The number of a row or a column is out of bounds!"));
        if(isUnavailable)
            return ResponseEntity.badRequest()
                    .body(new Error("The ticket has been already purchased!"));
        else {
            UUID token = UUID.randomUUID();
            Purchased idTicket = new Purchased(purchase.getRow(), purchase.getColumn(), token);
            purchasedTickets.add(idTicket);
            return ResponseEntity.ok()
                    .body(idTicket);
        }

    }

    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody Map<String,String> tokenS){
        String token = tokenS.get("token");
        if (token.length() != 36){
            return ResponseEntity.badRequest()
                    .body(new Error("Wrong token!"));
        }

        try {UUID tokenUTest = UUID.fromString(token);}
        catch (NumberFormatException e){
            return ResponseEntity.badRequest()
                    .body(new Error("Wrong token!"));
        }

        UUID tokenU = UUID.fromString(token);
            for (Purchased tok:purchasedTickets) {
                if (tok.getToken().toString().equals(tokenU.toString())) {
                    Ticket ticket = purchasedTickets.stream().filter(e -> e.getToken().equals(tokenU)).findAny().orElseThrow().getTicket();
                    purchasedTickets.remove(new Purchased(ticket.getRow(), ticket.getColumn(), tokenU));
                    return ResponseEntity.ok().body(Map.of("ticket",ticket));
                }
            }

        return ResponseEntity.badRequest().body(new Error("Wrong token!"));
    }
    @GetMapping("/stats")
    public ResponseEntity returnTicket(@RequestParam(required = false) String password){
        if (!access.equals(password)){
            return new ResponseEntity(new Error("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        int income = purchasedTickets.stream().map(ticket->ticket.getTicket().getPrice()).mapToInt(Integer::intValue).sum();
        int purchased = purchasedTickets.size();
        int available = cinema.getRows()* cinema.getColumns()-purchasedTickets.size();
        return new ResponseEntity(Map.of("income",income,"available",available,"purchased",purchased),HttpStatus.OK);
    }
    @GetMapping("/taken")
    public List<Purchased> getTaken() {
        return purchasedTickets;
    }
}




