package de.knowhow.base;

/*
 * This class holds all the positions of the view elements
 */
public class ViewConstants {

	// Splash-Screen
	public static int SPLASH_WIDTH = 300;
	public static int SPLASH_HEIGTH = 100;

	// MAINFRAME
	public static int MAIN_WIDTH = 600;
	public static int MAIN_HEIGTH = 480;

	// Menu
	public static int MENU_WIDTH = MAIN_WIDTH - 10;
	public static int MENU_HEIGTH = 25;

	// Tree
	public static int TREE_WIDTH = 150;
	public static int TREE_HEIGTH = MAIN_HEIGTH - MENU_HEIGTH - 10;

	// TopicChooser
	public static int TOPICCHOOSE_WIDTH = MAIN_WIDTH - TREE_WIDTH - 10;
	public static int TOPICCHOOSE_HEIGTH = 25;

	// ArticleLastEdit
	public static int LASTEDIT_WIDTH = MAIN_WIDTH - TREE_WIDTH - 10;
	public static int LASTEDIT_HEIGTH = 25;

	// ArticleRender
	public static int ARTRENDER_WIDTH = MAIN_WIDTH - TREE_WIDTH - 10;
	public static int ARTRENDER_HEIGTH = MAIN_HEIGTH - MENU_HEIGTH
			- TOPICCHOOSE_HEIGTH - LASTEDIT_HEIGTH - 20;

	// ArticlePlain
	public static int ARTPLAIN_WIDTH = MAIN_WIDTH - TREE_WIDTH - 10;
	public static int ARTPLAIN_HEIGTH = MAIN_HEIGTH - MENU_HEIGTH
			- TOPICCHOOSE_HEIGTH - LASTEDIT_HEIGTH - 20;

	// Rename
	public static int RENAME_WIDTH = 300;
	public static int RENAME_HEIGTH = 75;

	// AttachmentsForArticleView
	public static int ATTACH_WIDTH = 400;
	public static int ATTACH_HEIGTH = 400;

	// ArticleLinkView
	public static int ARTLINK_WIDTH = 400;
	public static int ARTLINK_HEIGTH = 400;

	// Subtopic
	public static int SUBTOPIC_WIDTH = 350;
	public static int SUBTOPIC_HEIGTH = 80;

	// CSSPlainView
	public static int CSSPLAIN_WIDTH = 400;
	public static int CSSPLAIN_HEIGTH = 400;

	// AboutView
	public static int ABOUT_WIDTH = 400;
	public static int ABOUT_HEIGTH = 350;
}
