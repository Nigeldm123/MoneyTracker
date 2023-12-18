package controller;

public interface ControllerInterface<T> {
    void addEntry(T entry);
    void removeEntry(T entry);
}
