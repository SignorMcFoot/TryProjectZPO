package sample;


/**
 * An interface essential to the Observer pattern usage
 * It has methods which allow to add, remove and update the observers which are the classes that will execute when notified
 */

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void updateObservers();

}
