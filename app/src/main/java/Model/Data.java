package Model;

public class Data {
     String title ;
     String price ;
     String image ;
     String ammount;
     String id ;

    public Data()
    {

    }

    public Data(String title, String price,String ammount, String image, String id) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.ammount=ammount;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getAmmount() {
        return ammount;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }
}
