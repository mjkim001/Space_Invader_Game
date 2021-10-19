public class BulletObject extends GameObject{
    int bulletPosY;

    BulletObject(int posX, int posY, String image) {
        super(posX, posY, image);
        this.bulletPosY = posY;
    }

    void changePosY() {
        bulletPosY--;
    }
}
