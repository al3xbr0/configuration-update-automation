package automation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Column implements Serializable {

    private static final String VARCHAR = "varchar";
    private static final String CHARACTER_VARYING = "character varying";

    private final String name;
    private final String dataType;
    private final Integer charMaxLen;

    public String getName() {
        return name;
    }

    @JsonProperty("dataType")
    public String getPrintableDataType() {
        return charMaxLen != null ?
                String.format("%s(%d)", dataType, charMaxLen) : dataType;
    }

    public Column(String name, String dataType, Integer charMaxLen) {
        this.name = name;
        this.dataType = dataType;
        this.charMaxLen = charMaxLen;
    }

    public Column(String name, String dataType) {
        this(name, dataType, null);
    }

    @JsonIgnore
    public boolean isVarchar() {
        return VARCHAR.equals(dataType);
    }

    public Column toCharacterVarying() {
        if (isVarchar()) {
            return new Column(name, CHARACTER_VARYING, charMaxLen);
        }
        throw new IllegalStateException("Datatype must be " + VARCHAR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (!name.equals(column.name)) return false;
        if (!dataType.equals(column.dataType)) return false;
        return Objects.equals(charMaxLen, column.charMaxLen);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + dataType.hashCode();
        result = 31 * result + (charMaxLen != null ? charMaxLen.hashCode() : 0);
        return result;
    }
}
