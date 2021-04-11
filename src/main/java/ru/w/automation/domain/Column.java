package ru.w.automation.domain;

public class Column {

    private final String name;
    private final String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "\"" + name + "\"" + ":" +
                "\"" + type + "\"" + "\n";
    }
}
