package cinema;

import java.util.ArrayList;
import java.util.List;

public class Cinema {

    private int rows = 9;
    private int columns = 9;
    private List<Ticket> seats = new ArrayList<>();
    private static Cinema cinema;


    private Cinema() {
        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.columns; j++) {
                this.seats.add(new Ticket(i, j));
            }
        }
    }

    public static Cinema getInstance() {
        if(cinema == null)
            cinema = new Cinema();
        return cinema;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
    public List<Ticket> getSeats() {
        return seats;
    }


}