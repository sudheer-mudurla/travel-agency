import java.util.*;
import java.util.List;

class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private int remainingCapacity;

    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.remainingCapacity = capacity;
    }

    public boolean signUp() {
        if (remainingCapacity > 0) {
            remainingCapacity--;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public String getDescription() {
        return description;
    }
}

class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public String getName() {
        return name;
    }
}

class Passenger {
    private String name;
    private int passengerNumber;
    private double balance;
    private PassengerType type;
    private List<Activity> signedUpActivities;

    public Passenger(String name, int passengerNumber, PassengerType type) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.type = type;
        this.signedUpActivities = new ArrayList<>();
        this.balance = 0.0;
    }

    public void signUpForActivity(Activity activity) {
        if (type == PassengerType.STANDARD) {
            if (balance >= activity.getCost()) {
                balance -= activity.getCost();
                signedUpActivities.add(activity);
                activity.signUp();
            }
        } else if (type == PassengerType.GOLD) {
            double discountedCost = 0.9 * activity.getCost();
            if (balance >= discountedCost) {
                balance -= discountedCost;
                signedUpActivities.add(activity);
                activity.signUp();
            }
        } else {
            // Premium passenger can sign up for free
            signedUpActivities.add(activity);
            activity.signUp();
        }
    }

    public String getName() {
        return name;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public double getBalance() {
        return balance;
    }

    public List<Activity> getSignedUpActivities() {
        return signedUpActivities;
    }
}

enum PassengerType {
    STANDARD,
    GOLD,
    PREMIUM
}

class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Destination> destinations;
    private List<Passenger> passengers;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.destinations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        destinations.add(destination);
    }

    public void addPassenger(Passenger passenger) {
        if (passengers.size() < passengerCapacity) {
            passengers.add(passenger);
        } else {
            System.out.println("Passenger limit reached. Cannot add more passengers.");
        }
    }

    public void printItinerary() {
        System.out.println("Travel Package: " + name);
        for (Destination destination : destinations) {
            System.out.println("Destination: " + destination.getName());
            for (Activity activity : destination.getActivities()) {
                System.out.println("   Activity: " + activity.getName());
                System.out.println("      Cost: Rs." + activity.getCost());
                System.out.println("      Capacity: " + activity.getCapacity());
                System.out.println("      Description: " + activity.getDescription());
            }
        }
    }

    public void printPassengerList() {
        System.out.println("Passenger List for Travel Package: " + name);
        System.out.println("Capacity: " + passengerCapacity);
        System.out.println("Number of passengers enrolled: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println("   Passenger: " + passenger.getName() + " (*" + passenger.getPassengerNumber() + ")");
        }
    }

    public void printIndividualPassengerDetails(int passengerNumber) {
        System.out.println("Details for Passenger *" + passengerNumber);
        for (Passenger passenger : passengers) {
            if (passenger.getPassengerNumber() == passengerNumber) {
                System.out.println("   Name: " + passenger.getName());
                System.out.println("   Passenger Number: " + passenger.getPassengerNumber());
                System.out.println("   Balance: Rs." + passenger.getBalance());
                System.out.println("   Signed Up Activities:");
                for (Activity activity : passenger.getSignedUpActivities()) {
                    System.out.println("      Activity: " + activity.getName());
                    System.out.println("         Destination: " + findDestinationByActivity(activity).getName());
                    System.out.println("         Price: Rs." + activity.getCost());
                }
            }
        }
    }

    public void printAvailableActivities() {
        System.out.println("Available Activities for Travel Package: " + name);
        for (Destination destination : destinations) {
            for (Activity activity : destination.getActivities()) {
                if (activity.getRemainingCapacity() > 0) {
                    System.out.println("   Activity: " + activity.getName() + " at " + destination.getName());
                    System.out.println("      Remaining Capacity: " + activity.getRemainingCapacity());
                }
            }
        }
    }

    private Destination findDestinationByActivity(Activity activity) {
        for (Destination destination : destinations) {
            if (destination.getActivities().contains(activity)) {
                return destination;
            }
        }
        
        return null;
    }
}

public class Travels {
    public static void main(String[] args) {
      
        TravelPackage travelPackage = new TravelPackage("Its Vacation Time", 50);

        Destination destination1 = new Destination("Gokarna");
        destination1.addActivity(new Activity("Boating", "Enjoy the waves", 50.0, 20));
        destination1.addActivity(new Activity("Horse Ride", "Enjoy the ride", 40.0, 15));

        Destination destination2 = new Destination("Pondicherry");
        destination2.addActivity(new Activity("Hiking", "Discover scenic trails", 30.0, 25));
        destination2.addActivity(new Activity("Beaches", "Enjoy the vibe and street food", 60.0, 10));

        travelPackage.addDestination(destination1);
        travelPackage.addDestination(destination2);

        travelPackage.addPassenger(new Passenger("Sudheer", 501, PassengerType.STANDARD));
        travelPackage.addPassenger(new Passenger("Vijaya Laxmi", 502, PassengerType.GOLD));
        travelPackage.addPassenger(new Passenger("Maggie", 503, PassengerType.PREMIUM));

        // Calling methods to display information
        travelPackage.printItinerary();
        travelPackage.printPassengerList();
        travelPackage.printIndividualPassengerDetails(1);
        travelPackage.printAvailableActivities();
    }
}
