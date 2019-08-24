package game.graphics;

import java.awt.image.BufferedImage;

public class Resources {
    private static final int width=64,height=64;

    public static BufferedImage world,world3,explosion,background,player1,player2,bullet,boundaryWall,breakableWall,breakableWall2,
            breakableWall3,breakableWall4,solidWall,solidWall2,solidWall3,
            barA,barA20,barA50,barA80,barA100,barB,barB20,barB50,barB80,barB100,bulletPU,healthPU,shieldPU,speedPU,world2;

    public static void init() {
        SpriteSheet sheet=new SpriteSheet(ImageLoader.loadImage("/textures/spriteSheet.png"));
        ImageLoader loader=new ImageLoader();

        player1=sheet.loadFromSheet(0,0,width,height);
        player2=sheet.loadFromSheet(width,0,width,height);
        bullet=sheet.loadFromSheet(width*2,0,width,height);
        boundaryWall=sheet.loadFromSheet(width*3,0,width,height);
        solidWall=sheet.loadFromSheet(0,height,width,height);
        solidWall2=sheet.loadFromSheet(width,height,width,height);
        solidWall3=sheet.loadFromSheet(width*2,height,width,height);
        breakableWall=sheet.loadFromSheet(width*3,height,width,height);
        breakableWall2=sheet.loadFromSheet(width,height*2,width,height);
        breakableWall3=sheet.loadFromSheet(width*2,height*2,width,height);
        breakableWall4=sheet.loadFromSheet(width*3,height*2,width,height);
        healthPU=sheet.loadFromSheet(0,height*3,width,height);
        shieldPU=sheet.loadFromSheet(width,height*3,width,height);
        bulletPU=sheet.loadFromSheet(width*2,height*3,width,height);
        speedPU=sheet.loadFromSheet(width*3,height*3,width,height);
        barA=loader.loadImage("/textures/barA.png");
        barA20=loader.loadImage("/textures/barA20.png");
        barA50=loader.loadImage("/textures/barA50.png");
        barA80=loader.loadImage("/textures/barA80.png");
        barA100=loader.loadImage("/textures/barA100.png");
        barB=loader.loadImage("/textures/barB.png");
        barB20=loader.loadImage("/textures/barB20.png");
        barB50=loader.loadImage("/textures/barB50.png");
        barB80=loader.loadImage("/textures/barB80.png");
        barB100=loader.loadImage("/textures/barB100.png");
        background=loader.loadImage("/textures/Background.bmp");
        explosion=loader.loadImage("/textures/Explosion_large.gif");
        world=loader.loadImage("/worlds/world.png");
        world2=loader.loadImage("/worlds/world2.png");
        world3=loader.loadImage("/worlds/world3.png");

    }
}
