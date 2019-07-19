package com.testmaps.testmaps;

public class ListViewContactItem {

    private String PickUpPoint="";
    private String Area = "";
    private String Landmark="";
    private String StreetName="";


    //Area
    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    //landmark
    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String Landmark) {
        this.Landmark = Landmark;
    }

    //PickUpPoint
    public void setPickUpPoint(String PickUpPoint) {
        this.PickUpPoint = PickUpPoint;
    }

    public String getPickUpPoint() {
        return PickUpPoint;
    }


    //streetname
    public void setStreetName(String StreetName) {
        this.StreetName = StreetName;
    }

    public String getStreetName() {
        return StreetName;
    }

}
