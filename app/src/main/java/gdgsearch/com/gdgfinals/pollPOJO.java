package gdgsearch.com.gdgfinals;

/**
 * Created by skmishra on 9/9/2017.
 */
public class pollPOJO  {

    String name;
    String about;
    String id;
    String image;

    @Override
    public String toString() {
        return "pollPOJO{" +
                "name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
