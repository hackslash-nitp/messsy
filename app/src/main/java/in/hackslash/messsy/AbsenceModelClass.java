package in.hackslash.messsy;

public class AbsenceModelClass {

    String startdate, enddate, name, roll, hostel;

    AbsenceModelClass(){}

    public AbsenceModelClass(String startdate, String enddate, String name, String roll, String hostel){
        this.startdate=startdate;
        this.enddate=enddate;
        this.name=name;
        this.roll=roll;
        this.hostel=hostel;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getsName() {
        return name;
    }

    public void setsName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }


    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }


}
