package PRAC2;
public class Parking_lot{
    private int two_wheelers=0,four_wheelers=0;
    private final int two_cap,four_cap;
    private static long revenue=0;
    public Parking_lot(int two_cap,int four_cap){
        this.two_cap=two_cap;
        this.four_cap=four_cap;
    }
    public void park(String type){
        if(type.equals("two")){
            if(two_wheelers<two_cap){
                two_wheelers++;
                revenue+=20;
                System.out.println("Parked two");
            }else{
                System.out.println("Full");
            }
        }else if(type.equals("four")){
            if(four_wheelers<four_cap){
                four_wheelers++;
                revenue+=40;
                System.out.println("Parked four");
            }else{
                System.out.println("Full");
            }
        }
    }
    public void leave(String type){
        if(type.equals("two")&&two_wheelers>0){
            two_wheelers--;
            System.out.println("Left two");
        }else if(type.equals("four")&&four_wheelers>0){
            four_wheelers--;
            System.out.println("Left four");
        }
    }
    public static void main(String[]args){
        Parking_lot lot=new Parking_lot(2,1);
        lot.park("two");
        lot.park("four");
        lot.park("four");
        lot.leave("two");
        lot.park("two");
        System.out.println("two_wheelers:"+lot.two_wheelers+" four_wheelers:"+lot.four_wheelers);
        System.out.println("Revenue:"+revenue);
    }
}
