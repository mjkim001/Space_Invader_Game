public class GameObject {
    private int posX;
    private int posY;
    private String image;

    GameObject(int posX, int posY, String image) {
        this.posX = posX;
        this.posY = posY;
        this.image = image;
    }

    int getX() {
        return posX;
    }

    int getY() {
        return posY;
    }

    String getImage() {
        return image;
    }
}
