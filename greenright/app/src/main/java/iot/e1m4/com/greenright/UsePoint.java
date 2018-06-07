package iot.e1m4.com.greenright;

public class UsePoint {

    private String useTitle;
    private String usePoint;
    private String useDay;
    public UsePoint() {
    }

    public UsePoint(String useTitle, String usePoint, String useDay) {
        this.useTitle = useTitle;
        this.usePoint = usePoint;
        this.useDay = useDay;
    }

    public String getUseTitle() {
        return useTitle;
    }

    public void setUseTitle(String useTitle) {
        this.useTitle = useTitle;
    }

    public String getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(String usePoint) {
        this.usePoint = usePoint;
    }

    public String getUseDay() {
        return useDay;
    }

    public void setUseDay(String useDay) {
        this.useDay = useDay;
    }
}
