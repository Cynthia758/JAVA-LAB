package prac61;
interface Switchable {
    void on();
    void off();

    default void toggle() {
        on();
    }
}
class Fan implements Switchable {
    private boolean status = false;

    public void on() {
        status = true;
        System.out.println("Fan is ON");
    }

    public void off() {
        status = false;
        System.out.println("Fan is OFF");
    }
}
class Light implements Switchable {
    private boolean status = false;
    public void on() {
        status = true;
        System.out.println("Light is ON");
    }
    public void off() {
        status = false;
        System.out.println("Light is OFF");
    }
}
interface SwitchPolicy {
    boolean maySwitchOn(Switchable device, int hour);
}

public class main {
     public static void main(String[] args) {

        Switchable[] devices = {
            new Fan(),
            new Light()
        };

        for (Switchable device : devices) {
            device.toggle();
        }
        SwitchPolicy policy1 = new SwitchPolicy() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };
        SwitchPolicy policy2 = (device, hour) -> hour >= 8 && hour <= 20;
        System.out.println(policy1.maySwitchOn(devices[0], 10));
        System.out.println(policy2.maySwitchOn(devices[1], 23));
    }
}
