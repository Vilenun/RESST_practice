package cinema;

import java.util.Objects;
import java.util.UUID;

public class Purchased {
    private UUID token;
    private Ticket ticket;



    public Purchased(int row, int column, UUID token) {
        this.ticket = new Ticket(row,column);
        this.token = token;
    }
    public Purchased(int row, int column) {
        this.ticket = new Ticket(row,column);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchased purchased = (Purchased) o;
        return ticket.getRow() == purchased.getTicket().getRow() && ticket.getColumn() == purchased.getTicket().getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticket, token);
    }



    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
