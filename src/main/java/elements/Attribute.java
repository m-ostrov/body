package elements;

public class Attribute {
    private String key;
    private String value;
    public Attribute(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getAttributeString(){
        return key + "=" + value;
    }
}
