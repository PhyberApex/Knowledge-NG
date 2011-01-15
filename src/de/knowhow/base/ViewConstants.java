package de.knowhow.base;

public class ViewConstants {

	// Splash-Screen
	public static int SPLASH_WIDTH = 300;
	public static int SPLASH_HEIGTH = 100;

	// MAINFRAME
	public static int MAIN_WIDTH;
	public static int MAIN_HEIGTH;

	// Menu
	public static int MENU_WIDTH;
	public static int MENU_HEIGTH = 25;
	public static int MENU_POS_X = 0;
	public static int MENU_POS_Y = 0;

	// Render
	public static int RENDER_WIDTH = 25;
	public static int RENDER_HEIGTH = 25;
	public static int RENDER_POS_Y = 0;

	// Plain
	public static int PLAIN_WIDTH = 25;
	public static int PLAIN_HEIGTH = 25;
	public static int PLAIN_POS_Y = 0;

	// Tree
	public static int TREE_WIDTH = 200;
	public static int TREE_HEIGTH;
	public static int TREE_POS_X = 5;
	public static int TREE_POS_Y = MENU_HEIGTH;

	// ArticleRender
	public static int ARTRENDER_WIDTH;
	public static int ARTRENDER_HEIGTH;
	public static int ARTRENDER_POS_X;
	public static int ARTRENDER_POS_Y = MENU_HEIGTH;

	// ArticlePlain
	public static int ARTPLAIN_WIDTH;
	public static int ARTPLAIN_HEIGTH;
	public static int ARTPLAIN_POS_X;
	public static int ARTPLAIN_POS_Y = MENU_HEIGTH;

	// TopicChooser
	public static int TOPICCHOOSE_WIDTH;
	public static int TOPICCHOOSE_HEIGTH;
	public static int TOPICCHOOSE_POS_X;
	public static int TOPICCHOOSE_POS_Y;

	// Rename
	public static int RENAME_WIDTH = 300;
	public static int RENAME_HEIGTH = 75;

	// AttachmentsForArticleView
	public static int ATTACH_WIDTH = 300;
	public static int ATTACH_HEIGTH;

	// ArticleLinkView
	public static int ARTLINK_WIDTH = 300;
	public static int ARTLINK_HEIGTH;

	// Subtopic
	public static int SUBTOPIC_WIDTH = 350;
	public static int SUBTOPIC_HEIGTH = 80;

	// CSSPlainView
	public static int CSSPLAIN_WIDTH;
	public static int CSSPLAIN_HEIGTH;

	public static void reload(Config conf) {
		int res = Integer.parseInt(conf.getProperty("resolution"));
		switch (res) {
		case 1:
			MAIN_WIDTH = 600;
			MAIN_HEIGTH = 480;
			break;
		case 2:
			MAIN_WIDTH = 800;
			MAIN_HEIGTH = 600;
			break;
		case 3:
			MAIN_WIDTH = 1024;
			MAIN_HEIGTH = 768;
			break;
		default:
			MAIN_WIDTH = 600;
			MAIN_HEIGTH = 480;
		}
		// Menu
		MENU_WIDTH = MAIN_WIDTH - 10/* FrameBuffer */- 50/* ButtonBuffer */- 250 /* SearchBuffer */;

		// Tree
		TREE_HEIGTH = MAIN_HEIGTH - MENU_HEIGTH;
		TREE_POS_Y = MENU_HEIGTH;

		// ArticleRender
		ARTRENDER_WIDTH = MAIN_WIDTH - TREE_WIDTH - 5;
		ARTRENDER_HEIGTH = MAIN_HEIGTH - MENU_HEIGTH/* MenueBuffer */- 40/* TopicchooserBuffer */;
		ARTRENDER_POS_X = TREE_POS_X + TREE_WIDTH;
		ARTRENDER_POS_Y = MENU_HEIGTH;

		// ArticlePlain
		ARTPLAIN_WIDTH = MAIN_WIDTH - TREE_WIDTH - 5;
		ARTPLAIN_HEIGTH = MAIN_HEIGTH - MENU_HEIGTH/* MenuBuffer */- 65/* TopicChooserBuffer */;
		ARTPLAIN_POS_X = TREE_POS_X + TREE_WIDTH - 5;
		ARTPLAIN_POS_Y = MENU_HEIGTH/* MenuBuffer */;

		// TopicChooser
		TOPICCHOOSE_WIDTH = MAIN_WIDTH - TREE_WIDTH - 5;
		TOPICCHOOSE_HEIGTH = MAIN_HEIGTH - ARTPLAIN_POS_Y - ARTPLAIN_HEIGTH;
		TOPICCHOOSE_POS_X = TREE_POS_X + TREE_WIDTH - 5;
		TOPICCHOOSE_POS_Y = ARTPLAIN_POS_Y + ARTPLAIN_HEIGTH + 5;

		// AttachmentsForArticleView
		ATTACH_HEIGTH = MAIN_HEIGTH / 2;

		// ArticleLinkView
		ARTLINK_HEIGTH = MAIN_HEIGTH / 2;

		// CSSPlainView
		CSSPLAIN_WIDTH = MAIN_WIDTH / 3 * 2;
		CSSPLAIN_HEIGTH = MAIN_HEIGTH / 3 * 2;
	}
}
