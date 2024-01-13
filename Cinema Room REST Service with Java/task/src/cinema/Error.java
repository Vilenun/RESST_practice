package cinema;

public class Error {
    //SOLD("The ticket has been already purchased!");
    private String error;

    public Error() {}
    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
