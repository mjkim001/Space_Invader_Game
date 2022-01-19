public class BulletObject extends GameObject{
    
    BulletObject(int posX, int posY, String image) {
        super(posX, posY, image);
    }

    void changePosY() {
        posY--;
    }
}
