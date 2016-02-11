package mobile.cliniconline.controlpoint;

/**
 * Created by caitano on 04/02/16.
 */
public class Clientlist {

    private String _id;
    private String fullname;
    private String phone;
    private String healthPlan;
    private String healthPlanNumber;
    private String schedulingDate;
    private String schedulingHour;

    public String get_id() {

        return this._id;
    }

    public String getFullname() {
        return this.fullname;
    }

    public String getPhone() {

        return this.phone;
    }

    public String getHealthPlan() {
        return this.healthPlan;
    }

    public String getHealthPlanNumber() {

        return this.healthPlanNumber;
    }

    public String getSchedulingDate() {

        return this.schedulingDate;
    }

    public String getSchedulingHour() {

        return this.schedulingHour;
    }
}