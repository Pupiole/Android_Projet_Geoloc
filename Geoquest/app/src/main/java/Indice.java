/**
 * Created by paul on 15/12/16.
 */

public class Indice {

   public String Image;

   public  String Text;

    public Indice (String Texte, String Image){

        this.Image = Image;
        this.Text = Texte;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }
}
