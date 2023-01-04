package lesson4.requestAddItem;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "item",
        "aisle",
        "parse"
})
@Generated("jsonschema2pojo")
public class RequestAddItem {

    @JsonProperty("item")
    private String item;
    @JsonProperty("aisle")
    private String aisle;
    @JsonProperty("parse")
    private Boolean parse;

    @JsonProperty("item")
    public String getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(String item) {
        this.item = item;
    }

    @JsonProperty("aisle")
    public String getAisle() {
        return aisle;
    }

    @JsonProperty("aisle")
    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    @JsonProperty("parse")
    public Boolean getParse() {
        return parse;
    }

    @JsonProperty("parse")
    public void setParse(Boolean parse) {
        this.parse = parse;
    }

}