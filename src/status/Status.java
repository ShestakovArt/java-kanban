package status;

public enum Status {
    NEW("NEW"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private String code;
    Status(String code){
        this.code = code;
    }
    public String getCode(){ return code;}
}
