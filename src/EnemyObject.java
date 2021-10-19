public class EnemyObject extends GameObject{
    int enemyPosX;
    int enemyPosY;

    EnemyObject(int posX, int posY, String image) {
        super(posX, posY, image);
        this.enemyPosX = posX;
        this.enemyPosY = posY;
    }
}
