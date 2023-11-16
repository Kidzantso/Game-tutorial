/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilz;

import entities.Crabby;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Game;
import main.GamePanel;
import static utilz.Constants.EnemyConstants.CRABBY;

/**
 *
 * @author dodom
 */
public class LoadSave {
    
    public static final String PLAYER_ATLAS = "res/player_sprites.PNG";
    public static final String LEVEL_ATLAS = "res/outside_sprites.PNG";
    public static final String PLAYING_BACKGROUND = "res/playing_bg_img.PNG";
    public static final String MENU_BUTTONS = "res/button_atlas.PNG";
    public static final String MENU_BACKGROUND = "res/menu_background.PNG";
    public static final String PAUSE_BACKGROUND = "res/pause_menu.PNG";
    public static final String URM_BUTTONS = "res/urm_buttons.PNG";
    public static final String SOUND_BUTTONS = "res/sound_button.PNG";
    public static final String VOLUME_BUTTONS = "res/volume_buttons.PNG";
    public static final String BACKGROUND_MENU_IMAGE = "res/background_menu.PNG";
    public static final String BIG_CLOUDS = "res/big_clouds.PNG";
    public static final String SMALL_CLOUDS = "res/small_clouds.PNG";
    public static final String CRABBY_SPRITE = "res/crabby_sprite.PNG";
    public static final String HEALTH_BAR = "res/health_power_bar.PNG";
    public static final String COMPLETED_IMAGE = "res/completed_sprite.PNG";
    public static final String POTION_ATLAS = "res/potions_sprites.PNG";
    public static final String CONTAINER_ATLAS = "res/objects_sprites.PNG";
    public static final String TRAP_ATLAS = "res/trap_atlas.PNG";
    public static final String CANNON_ATLAS = "res/cannon_atlas.PNG";
    public static final String BALL = "res/ball.PNG";
    public static final String DEATH_OVERLAY = "res/death_screen.PNG";
    public static final String OPTIONS_MENU = "res/options_background.PNG";
    
    public static BufferedImage GetSpriteAtlas(String FileName){
        BufferedImage img=null;
    try {
         img = ImageIO.read(new FileInputStream(FileName));
        } catch (IOException ex) {
            System.out.println("cant read");
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    return img;
        }
    public static BufferedImage[]GetAllLevels(){
        URL url = LoadSave.class.getResource("/lvls");
        File file =null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
        }
                File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];

			}

		BufferedImage[] imgs = new BufferedImage[filesSorted.length];

		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return imgs;
        }
    
    
    
}
