import de.knowhow.model.ArticleList;
import de.knowhow.model.Attachment;
import de.knowhow.model.AttachmentList;
import de.knowhow.model.TopicList;
import de.knowhow.model.db.DAO;
import de.knowhow.model.db.DAO_MYSQL;
import de.knowhow.model.db.DAO_SQLite;

public class TestData {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Keine Datenbank oder Typ angegeben.\n"
					+ "Param 1 = Datenbankname\n"
					+ "Param 2 = Typ (MYSQL/SQLITE)\n"
					+ "Programm wird beendet.");
			exit();
		} else {
			DAO db = null;
			if (args[1].equals("SQLITE")) {
				db = new DAO_SQLite();
				db.setDatabaseName(args[0]);
			} else if (args[1].equals("MYSQL")) {
				if (args.length < 5) {
					System.out
							.println("Zu wenige Parameter angegeben.\n"
									+ "Param 1 = Datenbankname\n"
									+ "Param 2 = Typ (MYSQL/SQLITE)\n"
									+ "Param 3 = Server\n" + "Param 4 = User\n"
									+ "Param 5 = Password\n"
									+ "Programm wird beendet.");
					exit();
				} else {

					db = new DAO_MYSQL();
					db.setDatabaseName(args[0]);
				}
			} else {
				System.out.println("Ungüliger Datenbanktyp\n"
						+ "Programm wird beendet");
				exit();
			}
			try {
				db.openDB();
				db.checkDB();
				TopicList topList = new TopicList(db);
				for (int i = 0; i < 20; i++) {
					topList.newTopic();
					topList.getCurrTopic().setName("TestThema" + i);
					if (i >= 10) {
						topList.getCurrTopic().setTopic_ID_FK(i % 5+1);
					}
				}
				ArticleList artList = new ArticleList(db);
				for (int i = 0; i < 50; i++) {
					artList.newArticle();
					artList.getCurrArticle().setName("TestArtikel" + i);
					artList.getCurrArticle().setContent("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu f");
					if (i > 15) {
						artList.getCurrArticle().setTopic_ID_FK(i % 15 + 1);
					} else {
						artList.getCurrArticle().setTopic_ID_FK(i);
					}
				}
				AttachmentList attList = new AttachmentList(db);
				for (int i = 0; i < 50; i++) {
					byte[] bb = { new Byte("2"), new Byte("4") };
					attList.addAttachment(new Attachment(db, "TestAnhang" + i,
							i % 3 + 1, bb, false));
				}
				for (int i = 0; i < 10; i++) {
					byte[] bb = { new Byte("2"), new Byte("4") };
					attList.addAttachment(new Attachment(db, "TestAnhang" + i,
							i % 3 + 1, bb, true));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void exit() {
		System.exit(0);
	}
}
