package com.example.signify;

//public class SignHelper {
//    int image;
//    String title;
//
//
//    public SignHelper(int image,String title) {
//        this.image = image;
//        this.title = title;
//
//    }
//
//    public int getImage() {
//        return image;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//}

/*** Testing Some Big Brain Thing ***/
public class SignHelper {
    String imageUrl;
    String title;
    String description1;
    String description2;
    String severityValue;

    public SignHelper(){

    }

    public SignHelper(String imageUrl,String title, String description1, String description2, String severityValue) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description1 = description1;
        this.description2 = description2;
        this.severityValue = severityValue;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription1(){
        return  description1;
    }

    public void setDescription1(String description1){
        this.description1 = description1;
    }

    public String getDescription2(){
        return  description2;
    }

    public void setDescription2(String description2){
        this.description2 = description2;
    }

    public String getSeverityValue(){
        return  severityValue;
    }

    public void setSeverityValue(String severityValue){
        this.severityValue = severityValue;
    }
}
/*** End Of Testing Big Brain Thing ***/
