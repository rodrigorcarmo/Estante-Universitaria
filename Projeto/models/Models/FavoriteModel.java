
package Models;

import com.google.gson.JsonObject;


public class FavoriteModel {
    
    UserModel um;
    JsonObject ad;
    JsonObject book;

    public UserModel getUm() {
        return um;
    }

    public void setUm(UserModel um) {
        this.um = um;
    }

    public JsonObject getAd() {
        return ad;
    }

    public void setAd(JsonObject ad) {
        this.ad = ad;
    }

    public JsonObject getBook() {
        return book;
    }

    public void setBook(JsonObject book) {
        this.book = book;
    }
    
    
    
}
