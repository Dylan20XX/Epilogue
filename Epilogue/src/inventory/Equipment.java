package inventory;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Armor;

public class Equipment {

	private ControlCenter c;
	private Player player;

	public Armor helmet;
	public Armor chest;
	public Armor leg;
	public Armor boot;
	public Armor gauntlet;

	public Animation currentHelmetLeftAnimation, currentHelmetRightAnimation;
	public Animation currentChestLeftAnimation, currentChestRightAnimation;
	public Animation currentLegLeftAnimation, currentLegRightAnimation;
	public Animation currentBootsLeftAnimation, currentBootsRightAnimation;
	public Animation currentGauntletsLeftAnimation, currentGauntletsRightAnimation;
	
	public Animation bronzeHL = new Animation(0, Assets.bronzeHelmetActive, true);
	public Animation bronzeHR = new Animation(0, CT.flip(Assets.bronzeHelmetActive), true);
	public Animation bronzeCL = new Animation(0, Assets.bronzeChestplateActive, true);
	public Animation bronzeCR = new Animation(0, CT.flip(Assets.bronzeChestplateActive), true);
	public Animation bronzeLL = new Animation(0, Assets.bronzeLeggingActive, true);
	public Animation bronzeLR = new Animation(0, CT.flip(Assets.bronzeLeggingActive), true);
	public Animation bronzeBL = new Animation(0, Assets.bronzeBootsActive, true);
	public Animation bronzeBR = new Animation(0, CT.flip(Assets.bronzeBootsActive), true);
	public Animation bronzeGL = new Animation(0, Assets.bronzeGauntletsActive, true);
	public Animation bronzeGR = new Animation(0, CT.flip(Assets.bronzeGauntletsActive), true);
	
	public Animation zincHL = new Animation(0, Assets.zincHelmetActive, true);
	public Animation zincHR = new Animation(0, CT.flip(Assets.zincHelmetActive), true);
	public Animation zincCL = new Animation(0, Assets.zincChestplateActive, true);
	public Animation zincCR = new Animation(0, CT.flip(Assets.zincChestplateActive), true);
	public Animation zincLL = new Animation(0, Assets.zincLeggingActive, true);
	public Animation zincLR = new Animation(0, CT.flip(Assets.zincLeggingActive), true);
	public Animation zincBL = new Animation(0, Assets.zincBootsActive, true);
	public Animation zincBR = new Animation(0, CT.flip(Assets.zincBootsActive), true);
	public Animation zincGL = new Animation(0, Assets.zincGauntletsActive, true);
	public Animation zincGR = new Animation(0, CT.flip(Assets.zincGauntletsActive), true);
	
	public Animation ironHL = new Animation(0, Assets.ironHelmetActive, true);
	public Animation ironHR = new Animation(0, CT.flip(Assets.ironHelmetActive), true);
	public Animation ironCL = new Animation(0, Assets.ironChestplateActive, true);
	public Animation ironCR = new Animation(0, CT.flip(Assets.ironChestplateActive), true);
	public Animation ironLL = new Animation(0, Assets.ironLeggingActive, true);
	public Animation ironLR = new Animation(0, CT.flip(Assets.ironLeggingActive), true);
	public Animation ironBL = new Animation(0, Assets.ironBootsActive, true);
	public Animation ironBR = new Animation(0, CT.flip(Assets.ironBootsActive), true);
	public Animation ironGL = new Animation(0, Assets.ironGauntletsActive, true);
	public Animation ironGR = new Animation(0, CT.flip(Assets.ironGauntletsActive), true);
	
	public Animation titaniumHL = new Animation(0, Assets.titaniumHelmetActive, true);
	public Animation titaniumHR = new Animation(0, CT.flip(Assets.titaniumHelmetActive), true);
	public Animation titaniumCL = new Animation(0, Assets.titaniumChestplateActive, true);
	public Animation titaniumCR = new Animation(0, CT.flip(Assets.titaniumChestplateActive), true);
	public Animation titaniumLL = new Animation(0, Assets.titaniumLeggingActive, true);
	public Animation titaniumLR = new Animation(0, CT.flip(Assets.titaniumLeggingActive), true);
	public Animation titaniumBL = new Animation(0, Assets.titaniumBootsActive, true);
	public Animation titaniumBR = new Animation(0, CT.flip(Assets.titaniumBootsActive), true);
	public Animation titaniumGL = new Animation(0, Assets.titaniumGauntletsActive, true);
	public Animation titaniumGR = new Animation(0, CT.flip(Assets.titaniumGauntletsActive), true);
	
	public Animation tungstenHL = new Animation(0, Assets.tungstenHelmetActive, true);
	public Animation tungstenHR = new Animation(0, CT.flip(Assets.tungstenHelmetActive), true);
	public Animation tungstenCL = new Animation(0, Assets.tungstenChestplateActive, true);
	public Animation tungstenCR = new Animation(0, CT.flip(Assets.tungstenChestplateActive), true);
	public Animation tungstenLL = new Animation(0, Assets.tungstenLeggingActive, true);
	public Animation tungstenLR = new Animation(0, CT.flip(Assets.tungstenLeggingActive), true);
	public Animation tungstenBL = new Animation(0, Assets.tungstenBootsActive, true);
	public Animation tungstenBR = new Animation(0, CT.flip(Assets.tungstenBootsActive), true);
	public Animation tungstenGL = new Animation(0, Assets.tungstenGauntletsActive, true);
	public Animation tungstenGR = new Animation(0, CT.flip(Assets.tungstenGauntletsActive), true);
	
	public Equipment(ControlCenter c, Player player) {

		this.c = c;
		this.player = player;

	}

	public void tick() {

		if(helmet != null && helmet.getName().equals("bronze helmet")) {
			currentHelmetLeftAnimation = bronzeHL;
			currentHelmetRightAnimation = bronzeHR;
		} else if(helmet != null && helmet.getName().equals("zinc helmet")) {
			currentHelmetLeftAnimation = zincHL;
			currentHelmetRightAnimation = zincHR;
		} else if(helmet != null && helmet.getName().equals("iron helmet")) {
			currentHelmetLeftAnimation = ironHL;
			currentHelmetRightAnimation = ironHR;
		} else if(helmet != null && helmet.getName().equals("titanium helmet")) {
			currentHelmetLeftAnimation = titaniumHL;
			currentHelmetRightAnimation = titaniumHR;
		} else if(helmet != null && helmet.getName().equals("tungsten helmet")) {
			currentHelmetLeftAnimation = tungstenHL;
			currentHelmetRightAnimation = tungstenHR;
		} else {
			currentHelmetLeftAnimation = null;
			currentHelmetRightAnimation = null;
		}
		
		if(chest != null && chest.getName().equals("bronze chestplate")) {
			currentChestLeftAnimation = bronzeCL;
			currentChestRightAnimation = bronzeCR;
		} else if(chest != null && chest.getName().equals("zinc chestplate")) {
			currentChestLeftAnimation = zincCL;
			currentChestRightAnimation = zincCR;
		} else if(chest != null && chest.getName().equals("iron chestplate")) {
			currentChestLeftAnimation = ironCL;
			currentChestRightAnimation = ironCR;
		} else if(chest != null && chest.getName().equals("titanium chestplate")) {
			currentChestLeftAnimation = titaniumCL;
			currentChestRightAnimation = titaniumCR;
		} else if(chest != null && chest.getName().equals("tungsten chestplate")) {
			currentChestLeftAnimation = tungstenCL;
			currentChestRightAnimation = tungstenCR;
		} else {
			currentChestLeftAnimation = null;
			currentChestRightAnimation = null;
		}
		
		if(leg != null && leg.getName().equals("bronze leggings")) {
			currentLegLeftAnimation = bronzeLL;
			currentLegRightAnimation = bronzeLR;
			
		} else if(leg != null && leg.getName().equals("zinc leggings")) {
			currentLegLeftAnimation = zincLL;
			currentLegRightAnimation = zincLR;
			
		} else if(leg != null && leg.getName().equals("iron leggings")) {
			currentLegLeftAnimation = ironLL;
			currentLegRightAnimation = ironLR;
			
		} else if(leg != null && leg.getName().equals("titanium leggings")) {
			currentLegLeftAnimation = titaniumLL;
			currentLegRightAnimation = titaniumLR;
			
		} else if(leg != null && leg.getName().equals("tungsten leggings")) {
			currentLegLeftAnimation = tungstenLL;
			currentLegRightAnimation = tungstenLR;
			
		} else {
			currentLegLeftAnimation = null;
			currentLegRightAnimation = null;
		}
		
		if(boot != null && boot.getName().equals("bronze boots")) {
			currentBootsLeftAnimation = bronzeBL;
			currentBootsRightAnimation = bronzeBR;
			
		} else if(boot != null && boot.getName().equals("zinc boots")) {
			currentBootsLeftAnimation = zincBL;
			currentBootsRightAnimation = zincBR;
			
		} else if(boot != null && boot.getName().equals("iron boots")) {
			currentBootsLeftAnimation = ironBL;
			currentBootsRightAnimation = ironBR;
			
		} else if(boot != null && boot.getName().equals("titanium boots")) {
			currentBootsLeftAnimation = titaniumBL;
			currentBootsRightAnimation = titaniumBR;
			
		} else if(boot != null && boot.getName().equals("tungsten boots")) {
			currentBootsLeftAnimation = tungstenBL;
			currentBootsRightAnimation = tungstenBR;
			
		} else {
			currentBootsLeftAnimation = null;
			currentBootsRightAnimation = null;
		}
		
		if(gauntlet != null && gauntlet.getName().equals("bronze gauntlets")) {
			currentGauntletsLeftAnimation = bronzeGL;
			currentGauntletsRightAnimation = bronzeGR;
			
		} else if(gauntlet != null && gauntlet.getName().equals("zinc gauntlets")) {
			currentGauntletsLeftAnimation = zincGL;
			currentGauntletsRightAnimation = zincGR;
			
		} else if(gauntlet != null && gauntlet.getName().equals("iron gauntlets")) {
			currentGauntletsLeftAnimation = ironGL;
			currentGauntletsRightAnimation = ironGR;
			
		} else if(gauntlet != null && gauntlet.getName().equals("titanium gauntlets")) {
			currentGauntletsLeftAnimation = titaniumGL;
			currentGauntletsRightAnimation = titaniumGR;
			
		} else if(gauntlet != null && gauntlet.getName().equals("tungsten gauntlets")) {
			currentGauntletsLeftAnimation = tungstenGL;
			currentGauntletsRightAnimation = tungstenGR;
			
		} else {
			currentGauntletsLeftAnimation = null;
			currentGauntletsRightAnimation = null;
		}
		
		if(currentHelmetLeftAnimation != null) 
			currentHelmetLeftAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentHelmetRightAnimation != null)
			currentHelmetRightAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentChestLeftAnimation != null)
			currentChestLeftAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentChestRightAnimation != null)
			currentChestRightAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentLegLeftAnimation != null)
			currentLegLeftAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentLegRightAnimation != null)
			currentLegRightAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentBootsLeftAnimation != null)
			currentBootsLeftAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentBootsRightAnimation != null)
			currentBootsRightAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentGauntletsLeftAnimation != null)
			currentGauntletsLeftAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		if(currentGauntletsRightAnimation != null)
			currentGauntletsRightAnimation.setIndex(player.getCurrentLeftAnimation().getIndex());
		
	}

	public void render(Graphics g) {

	}

}
