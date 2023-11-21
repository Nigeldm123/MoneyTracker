package entries;

public class PersonEntry extends Entry<PersonEntry> {
    private String name;
    public PersonEntry(PersonEntry template, String name) {
        super(template);
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
