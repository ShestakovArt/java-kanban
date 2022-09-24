package enums;

public enum TypeTask {
    TASK("TASK"),
    EPIC("EPIC"),
    SUBTASK("SUBTASK");

    private String code;
    TypeTask(String code){
        this.code = code;
    }
    public String getCode(){ return code;}
}
